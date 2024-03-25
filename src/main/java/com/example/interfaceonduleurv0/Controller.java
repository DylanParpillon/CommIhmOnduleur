package com.example.interfaceonduleurv0;

import com.example.interfaceonduleurv0.Distant.BddDistante;
import com.example.interfaceonduleurv0.SQl.SqlGestion;
import com.example.interfaceonduleurv0.onduleur.Wks;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jssc.SerialPortException;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class Controller implements Initializable {
    public BddDistante bddDistante =   new BddDistante("ws://10.0.0.172:8080/insertearnings");
    public Button buttonId;
    public VBox mainLayout;
    public Label labelBatterie;
    public Label labelPhotoEntrer;
    public Label LabelPSortie;
    public Label labelTensionSortie;
    public Label labelStatut;
    public Label labelGainI;
    public Label labelGainH;
    public Label LabelGainM;
    public Label labelGainJ;
    Timer bdt = new Timer();
     /*Scanner sc = new Scanner(System.in);
     SerialPort serialPort;*/

    Wks wks = new Wks(this);
    TimerTask timerTaskQPIGS = new TimerTask() {
        @Override
        public void run() {
            wks.demandeQPIGS();
        }
    };
    TimerTask timerTaskQPIRI = new TimerTask() {
        @Override
        public void run() {
            wks.demandeQPIRI();
        }
    };
    TimerTask timerTaskQPIWS = new TimerTask() {
        @Override
        public void run() {
            wks.demandeQPIWS();
        }
    };

    public Controller() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // new Thread(() -> {launchData();}).start();
        
        try {
            wks.initCom("COM7");
            wks.configurerParametres(2400, 8, 0, 1);
            bdt.scheduleAtFixedRate(timerTaskQPIGS, 0, 10000);
            bdt.scheduleAtFixedRate(timerTaskQPIRI,2000,10000);
            bdt.scheduleAtFixedRate(timerTaskQPIWS, 4000, 10000);
        } catch (SerialPortException e) {
            System.out.println(e.getExceptionType());
        }

        //rentrer d'une mesure nouvelle mesure
        new Thread(() -> {
            try {
            while (true) {
                SqlGestion sqlGestion = new SqlGestion();
                sqlGestion.mesure("2052.3", new Timestamp(System.currentTimeMillis()));
                System.out.println("mesure done !");
                Thread.sleep(60000);
            }
            } catch (InterruptedException | SQLException ex) {
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

