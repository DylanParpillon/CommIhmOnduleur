package com.example.interfaceonduleurv0;

import com.example.interfaceonduleurv0.RPI.ModeleQPIGS;
import com.example.interfaceonduleurv0.SQl.SqlGestion;

import com.example.interfaceonduleurv0.Distant.ReceptionGestion;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.*;


public class Controller implements Initializable {
    public ReceptionGestion receptionGestion =   new  ReceptionGestion("ws://10.0.0.172:8080/insertearnings");
    public Button buttonId;
    public VBox mainLayout;

    /*static Wks wks = new Wks();
    static Scanner sc = new Scanner(System.in);
    static SerialPort serialPort;*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // new Thread(() -> {launchData();}).start();

        //rentrer d'une mesure nouvelle mesure
        new Thread(() -> {
            try {
            SqlGestion sqlGestion = new SqlGestion();
            while (true) {
                sqlGestion.mesure(new ModeleQPIGS().getPuissanceActiveDeSortie_AC(), new Timestamp(System.currentTimeMillis()));
                System.out.println("mesure done !");
                Thread.sleep(60000);
            }
            } catch (SQLException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }).start();

        new Thread(() -> {
            String respond = null;
            try {
            SqlGestion sqlGestion = new SqlGestion();
            respond = receptionGestion.post(sqlGestion.lastValue());
            System.out.println(respond);
            } catch (SQLException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            while (true) {


                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();


    }





}

