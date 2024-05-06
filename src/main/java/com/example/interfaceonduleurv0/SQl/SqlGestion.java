package com.example.interfaceonduleurv0.SQl;

import com.example.interfaceonduleurv0.modeles.ModeleData;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Dydou P
 * Cette classe gère les opérations SQL telles que la récupération, l'insertion et la mise à jour
 * des données dans la base de données.
 */
public class SqlGestion {

    /** Instance de SqlConn pour la connexion à la base de données. */
    private SqlConn sqlConn = new SqlConn();

    /** Connexion à la base de données. */
    private Connection connection = sqlConn.getConnTestBdd();

    /** Requêtes SQL préparées pour différentes opérations. */
    private PreparedStatement requeteAll = connection.prepareStatement("SELECT * FROM calculs ORDER BY date ASC"),
    requete1 = connection.prepareStatement("SELECT * FROM calculs ORDER BY id_calcul DESC LIMIT ?"),
    requete2 = connection.prepareStatement("SELECT (JULIANDAY(?)  - JULIANDAY(?))* 24 delta"),
    requete3 = connection.prepareStatement("INSERT INTO calculs(energie,gain) VALUES(?,?)"),
    updatePrix = connection.prepareStatement("UPDATE prix SET prix = ? WHERE id_prix = 1"),
    mesureAcs = connection.prepareStatement("SELECT * FROM mesures"),
    switchUtoD = connection.prepareStatement("UPDATE mesures SET Ss_AC = ?, date = ? WHERE id_mesure = ?"),
    requeteDates = connection.prepareStatement("SELECT SUBSTR(date,0,5) AS annee FROM calculs GROUP BY annee ORDER BY annee DESC "),
    requeteAnnee = connection.prepareStatement("SELECT SUM(gain) gainYears, substr(date,1,4) temps ,substr(date,6,2) mois FROM calculs where temps = ?  and mois = ?"),
    requeteMois = connection.prepareStatement("SELECT SUM(gain) gainMonth, substr(date,1,7) temps FROM calculs where temps = ?"),
    requeteJour = connection.prepareStatement("SELECT SUM(gain) gainDay, substr(date,1,10) temps FROM calculs where temps = ?"),
    requeteHeure = connection.prepareStatement("SELECT SUM(gain) gainHours, substr(date,1,13) temps FROM calculs where temps = ?");

    /** Valeur d'énergie. */
    private double energie;

    /** Format de date utilisé pour la conversion. */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructeur par défaut.
     *
     * @throws SQLException si une erreur SQL se produit lors de la connexion à la base de données
     */
    public SqlGestion() throws SQLException {
    }
    /**
     * Récupère les données du mois précédent.
     *
     * @return les données du mois précédent
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    public Double getStatMonthOnYears(String annee , String month) throws SQLException {
        requeteAnnee.setString(1,annee);
        requeteAnnee.setString(2,month);
        ResultSet rs = requeteAnnee.executeQuery();
        return rs.getDouble("gainYears");
    }
    public String getlastMonth() throws SQLException {
        // Récupération du mois actuel
        String mois = sdf.format(new Timestamp(System.currentTimeMillis())).substring(0, 7);
        System.out.println(mois + "mois");

        // Préparation de la requête SQL pour récupérer les données du mois précédent
        requeteMois.setString(1, mois);

        // Exécution de la requête SQL et récupération des données du mois précédent
        return requeteMois.executeQuery().getString("gainMonth");
    }

    /**
     * Récupère les données de la dernière heure.
     *
     * @return les données de la dernière heure
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    public String getlastHours() throws SQLException {
        // Récupération de l'heure actuelle
        String heure = sdf.format(new Timestamp(System.currentTimeMillis())).substring(0, 13);
        System.out.println(heure + "heure");

        // Préparation de la requête SQL pour récupérer les données de la dernière heure
        requeteHeure.setString(1, heure);

        // Exécution de la requête SQL et récupération des données de la dernière heure
        return requeteHeure.executeQuery().getString("gainHours");
    }

    /**
     * Récupère les données du dernier jour.
     *
     * @return les données du dernier jour
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    public String getlastDay() throws SQLException {
        // Récupération du jour actuel
        String day = sdf.format(new Timestamp(System.currentTimeMillis())).substring(0, 10);
        System.out.println(day + "jour");

        // Préparation de la requête SQL pour récupérer les données du dernier jour
        requeteJour.setString(1, day);

        // Exécution de la requête SQL et récupération des données du dernier jour
        return requeteJour.executeQuery().getString("gainDay");
    }
    /**
     * Récupère toutes les dates disponibles dans la base de données.
     *
     * @return une liste de dates sous forme de chaînes de caractères
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    public ArrayList<String> getAllDate() throws SQLException {
        ArrayList<String> dates = new ArrayList<>();
        ResultSet rs = requeteDates.executeQuery();
        while (rs.next()) {
            dates.add(rs.getString("annee"));
        }
        return dates;
    }

    /**
     * Insère les nouvelles valeurs dans la base de données.
     *
     * @param newACs     les nouvelles valeurs à insérer
     * @return une liste de Données récupérées
     * @throws SQLException si une erreur SQL se produit lors de l'exécution des requêtes
     */
    public ArrayList<ModeleData> mesure(ArrayList<String> newACs) throws SQLException {
        ArrayList<ModeleData> dr = new ArrayList<>();
        Timestamp timestamp =  new Timestamp(System.currentTimeMillis());
        int i = 1;
        for (String newAC : newACs) {
            double newAcValue = Double.parseDouble(newAC);
            ArrayList<Timestamp> saveDate = new ArrayList<>();
            ArrayList<Double> saveAC = new ArrayList<>();
            ResultSet rs = mesureAcs.executeQuery();
            while (rs.next()) {
                Timestamp dateS = rs.getTimestamp("date");
                double AC = rs.getDouble("Ss_AC");
                saveDate.add(dateS);
                saveAC.add(AC);
            }
            switchUtoDM(saveAC.get(1), sdf.format(saveDate.get(1)), 1);
            switchUtoDM(newAcValue, sdf.format(timestamp), 2);
            requete2.setString(1,sdf.format(timestamp));
            requete2.setString(2,sdf.format(saveDate.get(1)));
            ResultSet rsDelta = requete2.executeQuery();
            double delta= 0;
            while (rsDelta.next()) {
                delta = rsDelta.getDouble("delta");
            }
            System.out.println("delta" + delta);
            if (delta != 0) energie = (saveAC.get(1) * delta)/1000;
            else energie = 0;
            ModeleData modeleData = stockValeur();
            dr.add(modeleData);
        }
        return dr;
    }

    /**
     * Met à jour les valeurs dans la table "mesures".
     *
     * @param ac    la nouvelle valeur à mettre à jour
     * @param date  la date à associer à la nouvelle valeur
     * @param i     l'indice de la valeur à mettre à jour
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    public void switchUtoDM(Double ac, String date, int i) throws SQLException {
        switchUtoD.setDouble(1, ac);
        switchUtoD.setString(2, date);
        switchUtoD.setInt(3, i);
        switchUtoD.executeUpdate();
    }

    /**
     * Met à jour le prix dans la table "prix".
     *
     * @param prix  le nouveau prix à définir
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    public void fixerPrix(double prix) throws SQLException {
        updatePrix.setDouble(1, prix);
        updatePrix.executeUpdate();
    }

    /**
     * Récupère toutes les valeurs de la table "calculs".
     *
     * @return une liste de Données récupérées
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    public ArrayList<ModeleData> getAllValue() throws SQLException {
        ArrayList<ModeleData> tblvalue = new ArrayList<>();
        ModeleData dc = new ModeleData();
        ResultSet rs = requeteAll.executeQuery();
        while (rs.next()) {
            dc.setDate(rs.getDate("date"));
            dc.setEuro(rs.getDouble("gain"));
            dc.setKilowatter(rs.getDouble("energie"));
            tblvalue.add(dc);
        }
        return tblvalue;
    }

    /**
     * Récupère la dernière valeur de la table "calculs".
     *
     * @return la dernière valeur sous forme de Donnée récupérée
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    public ModeleData getLastValue() throws SQLException {
        requete1.setInt(1, 1);
        ResultSet rs = requete1.executeQuery();
        ArrayList<ModeleData> lastValue = new ArrayList<>();
        ModeleData dc = new ModeleData();
        while (rs.next()) {
            dc.setMacAddress("00:00:00:00");
            dc.setDate(rs.getDate("date"));
            dc.setEuro(rs.getDouble("gain"));
            dc.setKilowatter(rs.getDouble("energie"));
        }
        return dc;
    }


    /**
     * Insère les valeurs stockées dans la base de données.
     *
     * @return les données récupérées
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    private ModeleData stockValeur() throws SQLException {
        ModeleData dr = new ModeleData();
        ResultSet rs = connection.prepareStatement("SELECT * FROM prix").executeQuery();
        double gain = rs.getDouble("prix") * energie;
        requete3.setDouble(1, gain);
        requete3.setDouble(2, energie);
        requete3.executeUpdate();
        dr.setEuro(gain);
        dr.setKilowatter(energie);
        return dr;
    }
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
