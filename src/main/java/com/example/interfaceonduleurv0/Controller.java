package com.example.interfaceonduleurv0;

import com.example.interfaceonduleurv0.Distant.BddDistante;
import com.example.interfaceonduleurv0.SQl.SqlGestion;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public BddDistante bddDistante =   new BddDistante("ws://10.0.0.172:8080/insertearnings");
    public Button buttonId;
    public VBox mainLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // new Thread(() -> {launchData();}).start();

        //rentrer d'une mesure nouvelle mesure
        new Thread(() -> {
            try {
            SqlGestion sqlGestion = new SqlGestion();
            while (true) {
                sqlGestion.mesure("2052.3", new Timestamp(System.currentTimeMillis()));
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
            respond = bddDistante.post(sqlGestion.lastValue());
            System.out.println(respond);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
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

