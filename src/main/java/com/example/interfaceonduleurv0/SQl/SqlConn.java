package com.example.interfaceonduleurv0.SQl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Cette classe gère la connexion à la base de données SQLite.
 */
public class SqlConn {

    /** Connexion à la base de données. */
    Connection conn = null;

    /**
     * Constructeur de la classe SqlConn.
     */
    public SqlConn() {
    }

    /**
     * Récupère la connexion à la base de données.
     *
     * @return la connexion à la base de données
     */
    public Connection getConnTestBdd() {
        try {
            // Chargement du pilote JDBC SQLite
            Class.forName("org.sqlite.JDBC");

            // Établissement de la connexion à la base de données
            conn = DriverManager.getConnection("jdbc:sqlite:/home/pi/onduleur.db");
            System.out.println("Connexion à la base de données établie avec succès.");
        } catch (SQLException | ClassNotFoundException e) {
            // Gestion des exceptions en cas d'erreur lors de la connexion
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
        return conn;
    }
}



