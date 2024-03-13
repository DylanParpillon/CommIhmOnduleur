package com.example.interfaceonduleurv0.Distant;

import com.example.interfaceonduleurv0.DonneRecup;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

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
        keys.add("macAddress");
        keys.add("date");
        keys.add("euro");
        keys.add("kilowatter");
        int j = 0;
//encodage des paramètres de la requête
        StringBuilder data = new StringBuilder("{\n");
        for (int i = 0; i < 4; i++) {
            if (j > 4) j = 0;
            if (i != 0) data.append(", \n");
            if (i <= 1)
                data.append("\"").append(URLEncoder.encode(keys.get(j), StandardCharsets.UTF_8)).append("\": \"").append(values.get(i)).append("\""); // ajouter la date stp parce t con
            else
                data.append("\"").append(URLEncoder.encode(keys.get(j), StandardCharsets.UTF_8)).append("\": ").append(URLEncoder.encode(values.get(i), StandardCharsets.UTF_8)); // ajouter la date stp parce t con
            j++;
        }
        data.append("\n}");
        System.out.println(data);
        return data.toString();
    }

    public String post(ArrayList<DonneRecup> values) {
        try {
        ArrayList<String> data = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        //pour chaque données les  mets en format json les stocks dans un tableau
        for (DonneRecup b : values) {
            data.add(objectMapper.writeValueAsString(b));
            System.out.println(data);
        }
        StringBuilder result = new StringBuilder();
        OutputStreamWriter writer = null;
        BufferedReader reader = null;

            URL url = new URL(adress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//création de la connection
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            System.out.println(data.get(0));
            conn.setRequestProperty("Content-Length", data.get(0));
            // Obtenir le flux de sortie pour écrire les données
            conn.setRequestProperty("Content-Length", data.get(0));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(data.get(0).getBytes());
        // Lire la réponse de la requête
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            while (in.readLine() != null) {
                response.append(in.readLine());
            }
            // Afficher la réponse
            System.out.println(response);return result.toString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

