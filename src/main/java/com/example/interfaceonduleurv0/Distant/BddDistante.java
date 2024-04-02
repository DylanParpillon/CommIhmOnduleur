package com.example.interfaceonduleurv0.Distant;

import com.example.interfaceonduleurv0.DonneRecup;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class BddDistante {
    private String adress2 = "http://10.0.0.172:8080/insertinverter"; // adresse serv quand onduleur create
    private String adress = "http://10.0.0.172:8080/insertearning"; // adresse serv
    HttpURLConnection connection ;

    public BddDistante(String adress) {
        //this.adress = adress;
    }
    public boolean connection () throws IOException {
        boolean connected ;
        URL url = new URL(adress);
        // Ouvrir une connexion HTTP
        connection =  (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() == 200) connected = true; else connected = false;
        return connected;
    }

    public String post(ArrayList<DonneRecup> values) {
        try {


            // Paramétrer la méthode de requête
            connection.setRequestMethod("POST");

            // Indiquer que le contenu de la requête est du JSON
            connection.setRequestProperty("Content-Type", "application/json");

            // Activer l'envoi et la réception de données
            connection.setDoOutput(true);
            ArrayList<String> data = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            //pour chaque données les  mets en format json les stocks dans un tableau
            for (DonneRecup b : values) {
                data.add(objectMapper.writeValueAsString(b));
                System.out.println(data);
            }
            // Obtenir le flux de sortie de la connexion

                try (OutputStream os = connection.getOutputStream()) {
                    for (String datat : data) {
                        // Écrire les données JSON dans le flux de sortie
                        byte[] input = datat.getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                        os.flush();
                    }
                }

            int responseCode = connection.getResponseCode();
                if (responseCode == 200) System.out.println("Les données on bienétais envoyer !!");

            // Fermer la connexion
            connection.disconnect();
            } catch(Exception e){
                System.err.println(e.getMessage());
            }

        return null;
    }
}

