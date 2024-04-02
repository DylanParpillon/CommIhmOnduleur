package com.example.interfaceonduleurv0.Distant;

import com.example.interfaceonduleurv0.DonneRecup;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Cette classe gère l'envoi de données à une base de données distante via des requêtes HTTP.
 */
public class BddDistante {

    /** Adresse du service distant pour l'insertion des données de l'onduleur. */
    private String addressInverter = "http://10.0.0.172:8080/insertinverter";

    /** Adresse du service distant pour l'insertion des gains. */
    private String addressEarning = "http://10.0.0.172:8080/insertearning";

    /** Connexion HTTP. */
    HttpURLConnection connection;

    /**
     * Constructeur de la classe BddDistante.
     *
     * @param address l'adresse du service distant
     */
    public BddDistante(String address) {
        //this.address = address;
    }

    /**
     * Établit une connexion avec le service distant.
     *
     * @return true si la connexion est établie avec succès, false sinon
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'établissement de la connexion
     */
    public boolean connection() throws IOException {
        URL url = new URL(addressEarning);
        connection = (HttpURLConnection) url.openConnection();
        boolean connected = (connection.getResponseCode() == 200);
        return connected;
    }

    /**
     * Envoie les données spécifiées à la base de données distante.
     *
     * @param values les valeurs à envoyer à la base de données
     * @return une chaîne de caractères représentant la réponse du serveur
     */
    public String post(ArrayList<DonneRecup> values) {
        try {
            // Paramètres de la requête HTTP POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Conversion des données en format JSON et envoi
            ObjectMapper objectMapper = new ObjectMapper();
            for (DonneRecup b : values) {
                String jsonData = objectMapper.writeValueAsString(b);
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(input, 0, input.length);
                    os.flush();
                }
            }

            // Récupération et traitement de la réponse du serveur
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Les données ont été envoyées avec succès !");
            }

            // Fermeture de la connexion
            connection.disconnect();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}