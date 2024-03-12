package com.example.interfaceonduleurv0.SQl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConn {
    Connection conn = null;
    public SqlConn() {

    }
    public Connection getConnTestBdd(){
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/didou/Desktop/Projet/JavaFX/interfaceOnduleurV0/src/onduleur.db");
            System.out.println("Connexion Sql done ");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage() + "   Erreur Connexion SQL");
        }
        return conn;
    }
}



