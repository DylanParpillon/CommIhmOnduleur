package com.example.interfaceonduleurv0;

import com.example.interfaceonduleurv0.Distant.BddDistante;
import com.example.interfaceonduleurv0.RPI.ModeleQPIGS;
import com.example.interfaceonduleurv0.RPI.ModeleQPIRI;
import com.example.interfaceonduleurv0.RPI.ModeleQPIWS;
import com.example.interfaceonduleurv0.SQl.SqlGestion;
import com.example.interfaceonduleurv0.onduleur.Wks;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jssc.SerialPortException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/** @author Dydou P
 * Cette classe est responsable de contrôler l'interface utilisateur de l'application, 
 * gérant les interactions avec l'utilisateur et les opérations nécessaires pour récupérer, 
 * afficher et enregistrer les données.
 * Elle implémente l'interface Initializable de JavaFX pour initialiser les composants de l'interface.
 */
public class Controller implements Initializable {

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
    ArrayList<DonneRecup> stockValeurEnvoie = new ArrayList<>();

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
    public Controller() throws SQLException {
    }

    /**
     * Méthode d'initialisation de la classe Controller.
     *
     * @param location  URL de localisation (non utilisé dans cette implémentation)
     * @param resources ResourceBundle (non utilisé dans cette implémentation)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // new Thread(() -> {launchData();}).start();
        try {
            wks.initCom("COM3");
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
        //mesure
        new Thread(()->{
            try {
                while (true) {
                    Thread.sleep(60000);
                    SqlGestion sqlGestion = new SqlGestion();
                    ArrayList<String> puissanceAc = new ArrayList<>();
                    for(ModeleQPIGS  qpigs : dataQPIGS){ puissanceAc.add(qpigs.getPuissanceActiveDeSortie_AC());}
                    saveTest = modeleQPIGS.getPuissanceActiveDeSortie_AC();
                    System.out.println("Puissance diff");
                    stockValeurEnvoie = sqlGestion.mesure(puissanceAc, new Timestamp(System.currentTimeMillis()));
                    System.out.println("Mesure effectuer");
                    updateGainGraph(sqlGestion);
                    if (bddDistante.connection()) {
                        bddDistante.post(stockValeurEnvoie);
                        stockValeurEnvoie.clear();
                        //mettre l'icone connexion
                    }else{
                        //mettre l'icone de co
                    }
                }
            } catch (SQLException e) {
                System.err.println(e + "erreur Sql");
            } catch (InterruptedException e) {
                System.err.println(e + " interrupted Excpt");
            } catch (IOException e) {
                System.err.println(e + "erreur connection? ");
            }
        }).start();
    }
    public void updateGainGraph(SqlGestion sqlGestion){
        // sqlGestion.getOneYears();
        Platform.runLater(()->{try {labelGainM.setText(sqlGestion.getlastMonth());} catch (SQLException ignored) {}});
        Platform.runLater(()->{try {labelGainJ.setText(sqlGestion.getlastDay());} catch (SQLException ignored) {}});
        Platform.runLater(()->{try {labelGainH.setText(sqlGestion.getlastHours());} catch (SQLException ignored) {}});
        Platform.runLater(()->{try {labelGainH.setText(String.valueOf(sqlGestion.getLastValue().getEuro()));} catch (SQLException ignored) {}});
    }
}