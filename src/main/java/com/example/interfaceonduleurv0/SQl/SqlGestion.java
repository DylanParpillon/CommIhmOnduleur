package com.example.interfaceonduleurv0.SQl;

import com.example.interfaceonduleurv0.Controller;
import com.example.interfaceonduleurv0.DonneRecup;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SqlGestion extends Controller {
    private SqlConn sqlConn = new SqlConn();
    private Connection connection = sqlConn.getConnTestBdd();
    private PreparedStatement requeteAll = connection.prepareStatement("SELECT * FROM calculs");
    private PreparedStatement requete1 = connection.prepareStatement("SELECT * FROM calculs ORDER BY id_calcul DESC LIMIT ?"),
            requete2 = connection.prepareStatement("SELECT * FROM calculs WHERE date <= ? and date >= ?"),
            requete3 = connection.prepareStatement("INSERT INTO calculs(id_calcul,energie,gain,date) VALUES(null,?,?,?)");
    private PreparedStatement updatePrix = connection.prepareStatement("UPDATE prix set prix = ? where id_prix = 1");

    private PreparedStatement mesureAcs = connection.prepareStatement("SELECT * FROM mesures"),
            switchUtoD = connection.prepareStatement("UPDATE mesures set Ss_AC = ? , date = ? where id_mesure = ?");
    private double energie;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SqlGestion() throws SQLException {
    }

    //insert les valeurs
    private void stockValeur() throws SQLException {
        ResultSet rs = connection.prepareStatement("SELECT * FROM prix").executeQuery();
        requete3.setDouble(1, energie);
        requete3.setDouble(2, rs.getDouble("prix") * energie);
        requete3.setString(3, sdf.format(new Timestamp(System.currentTimeMillis())));
        requete3.executeUpdate();
    }

    public void mesure(String nouvelAC, Timestamp timestamp) throws SQLException {
         double newAcValue = Double.valueOf(nouvelAC);
        ArrayList<Timestamp> saveDate = new ArrayList<>();
        ArrayList<Double> saveAC = new ArrayList<>();

        //save des 2 valeurs de la bdd dans des tableaux
        ResultSet rs = mesureAcs.executeQuery();
        while (rs.next()) {
            Timestamp dateS = rs.getTimestamp("date");
            double AC = rs.getDouble("Ss_AC");
            saveDate.add(dateS);
            saveAC.add(AC);
        }

        //faire remonter la valeur du dessous a au dessus
        switchUtoD.setDouble(1, saveAC.get(1));
        switchUtoD.setString(2, sdf.format(saveDate.get(1)));
        switchUtoD.setInt(3, 1);
        switchUtoD.executeUpdate();
        //calculer ac avec le delta
        double delta = Math.abs((saveDate.get(1).getTime() - saveDate.get(0).getTime()));
        if(delta != 0) energie = (saveAC.get(0) / delta) ;
        else energie = 0;
        stockValeur();

        //mettre la nouvelle valeur a 2
        switchUtoD.setDouble(1, newAcValue);
        switchUtoD.setString(2, sdf.format(timestamp));
        switchUtoD.setInt(3, 2);
        switchUtoD.executeUpdate();

    }

    public void fixerPrix(double prix) throws SQLException {
        updatePrix.setDouble(1, prix);
        updatePrix.executeUpdate();
    }
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
    public ArrayList<DonneRecup> lastValue() throws SQLException {
        requete1.setInt(1,1);
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

    public void twoLastValue() throws SQLException {
        ArrayList<Double> arEnergieTest = new ArrayList<>();
        ArrayList<Double> arGainTest = new ArrayList<>();
        requete1.setInt(1,2);
        ResultSet rs = requete1.executeQuery();

        while (rs.next()) {
            double energie = rs.getDouble("energie");
            double gain = rs.getDouble("gain");
            arGainTest.add(gain);
            arEnergieTest.add(energie);   //recup des 2 dernier valeur [0] la plus recentes
        }

    }

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


}
