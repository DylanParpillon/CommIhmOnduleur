package com.example.interfaceonduleurv0.SQl;

import com.example.interfaceonduleurv0.DonneRecup;

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
    private SqlConn sqlConn;

    /** Connexion à la base de données. */
    private Connection connection;

    /** Requêtes SQL préparées pour différentes opérations. */
    private PreparedStatement requeteAll, requete1, requete2, requete3, updatePrix, mesureAcs, switchUtoD, requeteDates;

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
        sqlConn = new SqlConn();
        connection = sqlConn.getConnTestBdd();
        requeteAll = connection.prepareStatement("SELECT * FROM calculs ORDER BY date ASC");
        requete1 = connection.prepareStatement("SELECT * FROM calculs ORDER BY id_calcul DESC LIMIT ?");
        requete2 = connection.prepareStatement("SELECT * FROM calculs WHERE date <= ? AND date >= ?");
        requete3 = connection.prepareStatement("UPDATE calculs SET date = ?, energie = ?, gain = ? WHERE ROWID = ?");
        updatePrix = connection.prepareStatement("UPDATE prix SET prix = ? WHERE id_prix = 1");
        mesureAcs = connection.prepareStatement("SELECT * FROM mesures");
        switchUtoD = connection.prepareStatement("UPDATE mesures SET Ss_AC = ?, date = ? WHERE id_mesure = ?");
        requeteDates = connection.prepareStatement("SELECT SUBSTR(date,0,5) AS annee FROM calculs GROUP BY annee ORDER BY annee DESC ");
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
     * @param timestamp  la date et l'heure à associer aux nouvelles valeurs
     * @return une liste de Données récupérées
     * @throws SQLException si une erreur SQL se produit lors de l'exécution des requêtes
     */
    public ArrayList<DonneRecup> mesure(ArrayList<String> newACs, Timestamp timestamp) throws SQLException {
        ArrayList<DonneRecup> dr = new ArrayList<>();
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
            double delta = Math.abs((saveDate.get(1).getTime() - saveDate.get(0).getTime()));
            if (delta != 0) energie = (saveAC.get(0) / delta);
            else energie = 0;
            DonneRecup donneRecup = stockValeur(sdf.format(timestamp));
            dr.add(donneRecup);
            switchUtoDM(newAcValue, sdf.format(timestamp), 2);
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
    public ArrayList<DonneRecup> getAllValue() throws SQLException {
        ArrayList<DonneRecup> tblvalue = new ArrayList<>();
        DonneRecup dc = new DonneRecup();
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
    public ArrayList<DonneRecup> lastValue() throws SQLException {
        requete1.setInt(1, 1);
        ResultSet rs = requete1.executeQuery();
        ArrayList<DonneRecup> lastValue = new ArrayList<>();
        DonneRecup dc = new DonneRecup();
        while (rs.next()) {
            dc.setMacAddress("00:00:00:00");
            dc.setDate(rs.getDate("date"));
            dc.setEuro(rs.getDouble("gain"));
            dc.setKilowatter(rs.getDouble("energie"));
            lastValue.add(dc);
        }
        return lastValue;
    }

    /**
     * Récupère les deux dernières valeurs de la table "calculs".
     *
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    public void twoLastValue() throws SQLException {
        ArrayList<Double> arEnergieTest = new ArrayList<>();
        ArrayList<Double> arGainTest = new ArrayList<>();
        requete1.setInt(1, 2);
        ResultSet rs = requete1.executeQuery();
        while (rs.next()) {
            double energie = rs.getDouble("energie");
            double gain = rs.getDouble("gain");
            arGainTest.add(gain);
            arEnergieTest.add(energie);   //récupération des deux dernières valeurs
        }
    }

    /**
     * Calcule le gain entre deux dates spécifiées.
     *
     * @param depart    la date de début
     * @param fin       la date de fin
     * @return le gain entre les deux dates
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    public double gainBtwDate(Date depart, Date fin) throws SQLException {
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Double> allGains = new ArrayList<>();
        requete2.setDate(1, fin);
        requete2.setDate(2, depart);
        ResultSet rs = requete2.executeQuery();
        while (rs.next()) {
            dates.add(rs.getDate("date"));
            allGains.add(rs.getDouble("gain"));
        }
        return allGains.get(allGains.size() - 1) - allGains.get(0);
    }

    /**
     * Insère les valeurs stockées dans la base de données.
     *
     * @param ts    le timestamp associé aux valeurs
     * @return les données récupérées
     * @throws SQLException si une erreur SQL se produit lors de l'exécution de la requête
     */
    private DonneRecup stockValeur(String ts) throws SQLException {
        DonneRecup dr = new DonneRecup();
        ResultSet rs = connection.prepareStatement("SELECT * FROM prix").executeQuery();
        double gain = rs.getDouble("prix") * energie;
        requete3.setString(1, ts);
        requete3.setDouble(2, energie);
        requete3.setDouble(3, gain);
        requete3.setInt(4, 1);
        requete3.executeUpdate();
        dr.setEuro(gain);
        dr.setKilowatter(energie);
        return dr;
    }
}
