package com.example.interfaceonduleurv0.Distant;

import com.example.interfaceonduleurv0.modeles.ModeleData;
import com.example.interfaceonduleurv0.modeles.ModeleInsert;
import com.example.interfaceonduleurv0.modeles.ModeleWarning;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Cette classe gère l'envoi de données à une base de données distante via des requêtes HTTP.
 */
public class BddDistante {

    /** Adresse du service distant pour l'insertion des données de l'onduleur. */
    private String addressInverter = "/insertinverter";

    /** Adresse du service distant pour l'insertion des gains. */
    private String addressEarning = "/insertearning";

    private String addressWarning = "/modifywarningsinverter";



    /**
     * Constructeur de la classe BddDistante.
     *
     */
    public BddDistante() {
        //this.address = address;
    }

    public boolean modifWarning(ModeleWarning modeleWarning , String urlS){
        try {
            URL url = new URL(urlS + addressInverter);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            if (modeleWarning != null) {
                co.setDoInput(true);
                co.setDoOutput(true);
                co.setRequestMethod("POST");
                co.addRequestProperty("Content-Type", "application/json");
                System.out.println("co :: " + co);
                co.setReadTimeout(10000);
                co.setConnectTimeout(15000);
                co.connect();
                // Conversion des données en format JSON et envoi
                ObjectMapper objectMapper = new ObjectMapper();
                OutputStream printout = co.getOutputStream();
                String jsonData = objectMapper.writeValueAsString(modeleWarning);
                System.out.println(jsonData);
                printout.write(jsonData.getBytes());
                System.out.println("envoyer");
                printout.close();
                int responseCode = co.getResponseCode();
                System.out.println(responseCode + " code reçu");
                if (responseCode == 200) {
                    System.out.println("Configuration a été envoyées avec succès !");
                    co.disconnect();
                    return true;
                }
                co.disconnect();
            }

            return false;
        }catch (IOException e ){
            return false;
        }

    }
    public boolean insertInverter(ModeleInsert modeleInsert , String urlS) {
        try {
            URL url = new URL(urlS + addressInverter);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            if (modeleInsert != null) {
                co.setDoInput(true);
                co.setDoOutput(true);
                co.setRequestMethod("POST");
                co.addRequestProperty("Content-Type", "application/json");
                System.out.println("co :: " + co);
                co.setReadTimeout(10000);
                co.setConnectTimeout(15000);
                co.connect();
                // Conversion des données en format JSON et envoi
                ObjectMapper objectMapper = new ObjectMapper();
                OutputStream printout = co.getOutputStream();
                String jsonData = objectMapper.writeValueAsString(modeleInsert);
                System.out.println(jsonData);
                printout.write(jsonData.getBytes());
                System.out.println("envoyer");
                printout.close();
                int responseCode = co.getResponseCode();
                System.out.println(responseCode + " code reçu");
                if (responseCode == 200) {
                    System.out.println("Configuration a été envoyées avec succès !");
                    co.disconnect();
                    return true;
                }
                co.disconnect();
            }

            return false;
        }catch (IOException e ){
            return false;
        }

    }
    /**
     * Envoie les données spécifiées à la base de données distante.
     *
     * @param values les valeurs à envoyer à la base de données
     */
    public boolean post(ModeleData values,  String ipServer) throws IOException {
        URL url;
        url = new URL(ipServer + addressEarning);
        //url = new URL(ipServer + addressEarning);
        HttpURLConnection co = (HttpURLConnection) url.openConnection();
        if(values != null) {
            co.setDoInput(true);
            co.setDoOutput(true);
            co.setRequestMethod("POST");
            co.addRequestProperty("Content-Type", "application/json");
            System.out.println("co :: " + co);
            co.setReadTimeout(10000);
            co.setConnectTimeout(15000);
            co.connect();
            // Conversion des données en format JSON et envoi
            ObjectMapper objectMapper = new ObjectMapper();
            OutputStream printout = co.getOutputStream();
                String jsonData = objectMapper.writeValueAsString(values);
                System.out.println(jsonData);
                printout.write(jsonData.getBytes());
                System.out.println("envoyer");
            printout.close();
            int responseCode = co.getResponseCode();
            System.out.println(responseCode + " code reçu");
            if (responseCode == 200) {
                System.out.println("données ont été envoyées avec succès !");
                co.disconnect();
                System.out.println("true");
                return true;
            }  co.disconnect();
        }
            return false;
    }


    }
