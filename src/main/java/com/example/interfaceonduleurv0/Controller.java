package com.example.interfaceonduleurv0;

import com.example.interfaceonduleurv0.Distant.BddDistante;
import com.example.interfaceonduleurv0.RPI.ModeleQPIGS;
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
import java.util.Objects;
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
    String saveTest;
     /*Scanner sc = new Scanner(System.in);
     SerialPort serialPort;*/

    Wks wks = new Wks();
    ModeleQPIGS modeleQPIGS = new ModeleQPIGS();
    TimerTask timerTaskQPIGS = new TimerTask(){
        @Override
        public void run() {
           modeleQPIGS =  wks.demandeQPIGS();
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
            bdt.scheduleAtFixedRate(timerTaskQPIGS, 60000, 10000);
            //bdt.scheduleAtFixedRate(timerTaskQPIRI,6000,10000);
            //bdt.scheduleAtFixedRate(timerTaskQPIWS, 6000, 10000);
        } catch (SerialPortException e) {
            System.out.println(e.getExceptionType());
        }
        new Thread(()->{
            try {
                while (true) {
                    Thread.sleep(60000);
                    SqlGestion sqlGestion = new SqlGestion();
                    if (modeleQPIGS.getPuissanceActiveDeSortie_AC() != null) {
                        System.out.println("modele no null " + modeleQPIGS.getPuissanceActiveDeSortie_AC());
                        if (!Objects.equals(modeleQPIGS.getPuissanceActiveDeSortie_AC(), saveTest)) {
                            saveTest = modeleQPIGS.getPuissanceActiveDeSortie_AC();
                            System.out.println("Puissance diff");
                            sqlGestion.mesure(modeleQPIGS.getPuissanceActiveDeSortie_AC(), new Timestamp(System.currentTimeMillis()));
                            System.out.println("Mesure effectuer");
                        }
                    }

                }
            } catch (SQLException e) {
                System.err.println(e + "erreur Sql");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
}

