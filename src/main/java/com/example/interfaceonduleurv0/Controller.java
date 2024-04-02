package com.example.interfaceonduleurv0;

import com.example.interfaceonduleurv0.Distant.BddDistante;
import com.example.interfaceonduleurv0.RPI.ModeleQPIGS;
import com.example.interfaceonduleurv0.RPI.ModeleQPIRI;
import com.example.interfaceonduleurv0.RPI.ModeleQPIWS;
import com.example.interfaceonduleurv0.SQl.SqlGestion;
import com.example.interfaceonduleurv0.onduleur.Wks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jssc.SerialPortException;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public ChoiceBox choiceBoxDate;
    public LineChart chartId;
    Timer bdt = new Timer();
    ArrayList<ModeleQPIGS> dataQPIGS = new ArrayList<>();
    ArrayList<ModeleQPIRI> dataQPIRI = new ArrayList<>();
    ArrayList<ModeleQPIWS> dataQPIWS = new ArrayList<>();
    String saveTest;
    ArrayList<DonneRecup> stockValeurEnvoie = new ArrayList<>();
     /*Scanner sc = new Scanner(System.in);
     SerialPort serialPort;*/

    Wks wks = new Wks(this);
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
            bdt.scheduleAtFixedRate(timerTaskQPIGS, 0, 10000);
            //bdt.scheduleAtFixedRate(timerTaskQPIRI,2000,10000);
            //bdt.scheduleAtFixedRate(timerTaskQPIWS, 4000, 10000);
        } catch (SerialPortException e) {
            System.out.println(e.getExceptionType());
        }
        new Thread(()->{
            try {
                SqlGestion sqlGestion = new SqlGestion();
                ArrayList<String> list = sqlGestion.getAllDate();
                System.out.println(list);
                ObservableList<String> ObList = FXCollections.observableList(list);
                choiceBoxDate.setItems(ObList);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }).start();
        /*new Thread(()->{
            try {
                while (true) {
                    Thread.sleep(6000);
                    SqlGestion sqlGestion = new SqlGestion();
                    if (modeleQPIGS.getPuissanceActiveDeSortie_AC() != null) {
                        System.out.println("modele no null " + modeleQPIGS.getPuissanceActiveDeSortie_AC());
                        if (!Objects.equals(modeleQPIGS.getPuissanceActiveDeSortie_AC(), saveTest)) {
                            saveTest = modeleQPIGS.getPuissanceActiveDeSortie_AC();
                            System.out.println("Puissance diff");
                            stockValeurEnvoie.add(sqlGestion.mesure(modeleQPIGS.getPuissanceActiveDeSortie_AC(), new Timestamp(System.currentTimeMillis())));
                            System.out.println("Mesure effectuer");
                        }
                    }
                }
            } catch (SQLException e) {
                System.err.println(e + "erreur Sql");
            } catch (InterruptedException e) {
               System.err.println(e + " interrupted Excpt");
            }
        }).start();*/ //mesure

        /*new Thread(()->{
            try {
                Thread.sleep(60000);
                bddDistante.post(stockValeurEnvoie);
                stockValeurEnvoie.clear();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();*/ //envoie

    }
}

