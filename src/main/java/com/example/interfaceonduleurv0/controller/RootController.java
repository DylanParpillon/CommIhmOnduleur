package com.example.interfaceonduleurv0.controller;

import com.example.interfaceonduleurv0.Distant.BddDistante;
import com.example.interfaceonduleurv0.Ihm;
import com.example.interfaceonduleurv0.SQl.SqlGestion;
import com.example.interfaceonduleurv0.modeles.ModeleData;
import com.example.interfaceonduleurv0.modeles.ModeleQPIGS;
import com.example.interfaceonduleurv0.modeles.ModeleQPIRI;
import com.example.interfaceonduleurv0.modeles.ModeleQPIWS;
import com.example.interfaceonduleurv0.onduleur.Wks;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jssc.SerialPortException;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/** @author Dydou P
 * Cette classe est responsable de contrôler l'interface utilisateur de l'application,
 * gérant les interactions avec l'utilisateur et les opérations nécessaires pour récupérer,
 * afficher et enregistrer les données.
 * Elle implémente l'interface Initializable de JavaFX pour initialiser les composants de l'interface.
 */
public class RootController implements Initializable {
    public CategoryAxis bottomLine;
    public NumberAxis topLine;
    Ihm ihm;
    public void setMainApp(Ihm ihm){
        this.ihm = ihm;
    }

    /** Référence à la base de données distante. */
    public BddDistante bddDistante = new BddDistante("ws://10.0.0.172:8080/insertearnings");

    /** Bouton de contrôle. */
    public Button buttonId;

    /** Disposition principale de l'interface. */
    public VBox mainLayout;

    /** Labels pour afficher les données. */
    public Label labelBatterie;
    public Label labelPhotoEntrer;
    public Label labelPSortie;
    public Label labelTensionSortie;
    public Label labelStatut;
    public Label labelGainI;
    public Label labelGainH;
    public Label labelGainM;
    public Label labelGainJ;

    /** Choix de la date. */
    public ChoiceBox choiceBoxDate;

    /** Graphique de ligne. */
    public LineChart chartId;
    public Button buttonServer;
    public Button ButtonSetting;

    /** Timer pour les tâches périodiques. */
    Timer bdt = new Timer();

    /** Liste pour stocker les données QPIGS. */
    public ArrayList<ModeleQPIGS> dataQPIGS = new ArrayList<>();

    /** Liste pour stocker les données QPIRI. */
    public ArrayList<ModeleQPIRI> dataQPIRI = new ArrayList<>();

    /** Liste pour stocker les données QPIWS. */
    public ArrayList<ModeleQPIWS> dataQPIWS = new ArrayList<>();

    /** Chaîne pour sauvegarder des tests. */
    String saveTest;

    /** Liste pour stocker les valeurs à envoyer. */
    ArrayList<ModeleData> stockValeurEnvoie = new ArrayList<>();

    /** Instance de la classe Wks pour la communication avec le matériel. */
    Wks wks = new Wks(this);

    /** Modèle pour les données QPIGS. */
    ModeleQPIGS modeleQPIGS = new ModeleQPIGS();

    /** Tâche périodique pour récupérer les données QPIGS. */
    TimerTask timerTaskQPIGS = new TimerTask(){
        @Override
        public void run() {
            modeleQPIGS =  wks.demandeQPIGS();
        }
    };

    /** Tâche périodique pour récupérer les données QPIRI. */
    TimerTask timerTaskQPIRI = new TimerTask() {
        @Override
        public void run() {
            wks.demandeQPIRI();
        }
    };

    /** Tâche périodique pour récupérer les données QPIWS. */
    TimerTask timerTaskQPIWS = new TimerTask() {
        @Override
        public void run() {
            wks.demandeQPIWS();
        }
    };

    /**
     * Constructeur par défaut.
     *
     * @throws SQLException si une erreur SQL se produit
     */
    public RootController() throws SQLException {
    }

    /**
     * Méthode d'initialisation de la classe RootController.
     *
     * @param location  URL de localisation (non utilisé dans cette implémentation)
     * @param resources ResourceBundle (non utilisé dans cette implémentation)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Calendar c = Calendar.getInstance();
        String year = String.valueOf(c.get(Calendar.YEAR));
        choiceBoxDate.setValue(year);
        Platform.runLater(()->{  try {lineChartStat(year);} catch (SQLException e) {}});
        ButtonSetting.setOnAction(event->{ihm.configView();});
        buttonServer.setOnAction((e)->{
            ihm.wifiView();
        });
        // new Thread(() -> {launchData();}).start();
        try {
            SqlGestion sqlGestion = new SqlGestion();
            ObservableList<String> ObList = FXCollections.observableList(sqlGestion.getAllDate());
            choiceBoxDate.setItems(ObList);
            wks.initCom("COM7");
            wks.configurerParametres(2400, 8, 0, 1);
            bdt.scheduleAtFixedRate(timerTaskQPIGS, 0, 10000);
            //bdt.scheduleAtFixedRate(timerTaskQPIRI,2000,10000);
            //bdt.scheduleAtFixedRate(timerTaskQPIWS, 4000, 10000);
        } catch (SerialPortException e) {
            System.err.println(e + " serialport");
        } catch (SQLException e ) {
            System.err.println(e + " sql erreur");
        }
        //mesure
        new Thread(()->{
            try {
                boolean flag = true;
                while (flag) {
                    Thread.sleep(60000);
                    SqlGestion sqlGestion = new SqlGestion();
                    ArrayList<String> puissanceAc = new ArrayList<>();
                    for(ModeleQPIGS  qpigs : dataQPIGS){ puissanceAc.add(qpigs.getPuissanceActiveDeSortie_AC());}
                    saveTest = modeleQPIGS.getPuissanceActiveDeSortie_AC();
                    System.out.println("Puissance diff");
                    stockValeurEnvoie = sqlGestion.mesure(puissanceAc, new Timestamp(System.currentTimeMillis()));
                    System.out.println("Mesure effectuer");
                    updateGainGraph(sqlGestion);
                    boolean dataStatue =  bddDistante.post(stockValeurEnvoie);
                    if (dataStatue) {
                        System.out.println("envoyer");
                        //mettre l'icone connexion
                        stockValeurEnvoie.clear();
                        dataQPIGS.clear();
                    }else {
                        System.out.println("echec envoie");
                        flag = false;
                    }

                }
            } catch (SQLException e) {
                System.err.println(e + "erreur Sql");
            } catch (InterruptedException e) {
                System.err.println(e + " interrupted Excpt");
            }
        }).start();
        choiceBoxDate.setOnAction(event->{
            System.out.println(choiceBoxDate.getValue());
            try {
                lineChartStat(choiceBoxDate.getValue().toString()  );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void updateGainGraph(SqlGestion sqlGestion){
        // sqlGestion.getOneYears();
        Platform.runLater(()->{try {labelGainM.setText(sqlGestion.getlastMonth());} catch (SQLException ignored) {}});
        Platform.runLater(()->{try {labelGainJ.setText(sqlGestion.getlastDay());} catch (SQLException ignored) {}});
        Platform.runLater(()->{try {labelGainH.setText(sqlGestion.getlastHours());} catch (SQLException ignored) {}});
        Platform.runLater(()->{try {labelGainH.setText(String.valueOf(sqlGestion.getLastValue().getEuro()));} catch (SQLException ignored) {}});
    }
    public  void lineChartStat(String annee) throws SQLException {
    SqlGestion sqlGestion = new SqlGestion();
    ArrayList<String> desMois = new ArrayList<>();
    NumberAxis xAxis = new NumberAxis(1, 12, 1);
    NumberAxis yAxis = new NumberAxis(0.00001, 0.01, 0.0005);
    chartId = new LineChart(xAxis, yAxis);
    for (int i = 1 ; i <= 12 ; i++){
        String  mois;
        if(i<10) mois = "0"+i;
        else mois = String.valueOf(i);
        double valeur;
        valeur = sqlGestion.getStatMonthOnYears(annee,mois);
        chartId.getData().add(new XYChart.Data<>(mois, valeur));
    }


}

}