package com.example.interfaceonduleurv0.Distant;

import com.example.interfaceonduleurv0.DonneRecup;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReceptionGestion {
    private String adress2 = "http://10.0.0.172:8080/insertinverter"; // adresse serv quand onduleur create
    private String adress = "http://10.0.0.172:8080/insertearning"; // adresse serv

    public ReceptionGestion(String adress) {
        //this.adress = adress;
    }

    private String jsonFormat(ArrayList<String> values) {
    ArrayList<String> keys = new ArrayList<>();
    keys.add("macAddress");keys.add("date");keys.add("euro");keys.add("kilowatter");
        int j = 0;
//encodage des paramètres de la requête
        StringBuilder data = new StringBuilder("{\n");
        for (int i = 0; i < 4; i++) {
            if(j >4) j = 0;
            if (i != 0) data.append(", \n");
            if(i <=1)
            data.append("\"").append(URLEncoder.encode(keys.get(j), StandardCharsets.UTF_8)).append("\": \"").append(values.get(i)).append("\""); // ajouter la date stp parce t con
            else
                data.append("\"").append(URLEncoder.encode(keys.get(j), StandardCharsets.UTF_8)).append("\": ").append(URLEncoder.encode(values.get(i), StandardCharsets.UTF_8)); // ajouter la date stp parce t con
            j++;
        }
        data.append("\n}");
        System.out.println(data);
        return data.toString();
}
    public  String post(ArrayList<DonneRecup> values) throws JsonProcessingException {
        ArrayList<String> data  = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        //pour chaque données les  mets en format json les stocks dans un tableau
        for (DonneRecup b: values) {
            data.add(objectMapper.writeValueAsString(b));
            System.out.println(data);
        }
        StringBuilder result = new StringBuilder();
        OutputStreamWriter writer = null;
        BufferedReader reader = null;
        try {
//création de la connection
            URL url = new URL(adress);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            for (String d:data) {
                byte[] postDataBytes = d.getBytes(StandardCharsets.UTF_8);
                System.out.println(d);
                // Définir les en-têtes de la requête
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                // Obtenir le flux de sortie pour écrire les données
                try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                    wr.write(postDataBytes);
                }

            }
            // Lire la réponse de la requête
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                // Afficher la réponse
                System.out.println(response);
            }

        }catch (Exception e) {
            System.out.println("c'est good " +  e.getMessage());
        }
        return result.toString();
    }
}

