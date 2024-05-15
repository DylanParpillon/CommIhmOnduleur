package com.example.interfaceonduleurv0.controller;

import com.example.interfaceonduleurv0.Distant.BddDistante;
import com.example.interfaceonduleurv0.Ihm;
import com.example.interfaceonduleurv0.SQl.SqlGestion;
import com.example.interfaceonduleurv0.modeles.*;
import com.example.interfaceonduleurv0.onduleur.LiaisonSerie;
import com.example.interfaceonduleurv0.onduleur.Wks;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jssc.SerialPortException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.sql.SQLException;
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
    public BddDistante bddDistante = new BddDistante();

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
    Timer bdt2 = new Timer();
    Timer bdt3 = new Timer();

    /** Liste pour stocker les données QPIGS. */
    public ArrayList<ModeleQPIGS> dataQPIGS = new ArrayList<>();

    /** Liste pour stocker les données QPIRI. */
    public ArrayList<ModeleQPIRI> dataQPIRI = new ArrayList<>();

    /** Liste pour stocker les données QPIWS. */
    public ArrayList<ModeleQPIWS> dataQPIWS = new ArrayList<>();

    /** Chaîne pour sauvegarder des tests. */
    String saveTest;
    SqlGestion sqlGestion = new SqlGestion();

    /** Liste pour stocker les valeurs à envoyer. */
  ModeleData stockValeurEnvoie = new ModeleData();

    /** Instance de la classe Wks pour la communication avec le matériel. */
    Wks wks = new Wks(this);

    /** Modèle pour les données QPIGS. */
    ModeleQPIGS modeleQPIGS = new ModeleQPIGS();

    /**
     * Constructeur par défaut.
     *
     * @throws SQLException si une erreur SQL se produit
     */
    public RootController() throws SQLException {
    }
    private File fichier;
    ModeleConfiguration m;

    /**
     * Méthode d'initialisation de la classe RootController.
     *
     * @param location  URL de localisation (non utilisé dans cette implémentation)
     * @param resources ResourceBundle (non utilisé dans cette implémentation)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        labelStatut.setText(new LiaisonSerie().listerLesPorts().toString());
        ButtonSetting.setOnAction(e -> {ihm.configView();});
        buttonServer.setOnAction((e) -> {ihm.wifiView();});
        new Thread(()->{
            fichier = new File("./config.bin");
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(fichier));
            m = (ModeleConfiguration) ois.readObject(); } catch (IOException | ClassNotFoundException e) {
                System.err.println(e);
        }
        }).start();
        try {
            ObservableList<String> ObList = FXCollections.observableList(sqlGestion.getAllDate());
            choiceBoxDate.setItems(ObList);
            //wks.initCom("/dev/ttyUSB0");
            wks.initCom("COM3");
            choiceBoxDate.setOnAction(event ->{
                System.out.println(choiceBoxDate.getValue());
                try {
                    lineChartStat(choiceBoxDate.getValue().toString());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            wks.configurerParametres(2400, 8, 0, 1);
            recupMesureOnduleur();
        } catch (SerialPortException e) {
            System.err.println(e + " serialport");
        } catch (SQLException e) {
            System.err.println(e + " sql erreur");
        }
        partiGraphique();
        TimerTask partieBddCalculs= new TimerTask() {
            @Override
            public void run() {try {
                        ArrayList<String> puissanceAc = new ArrayList<>();
                        for (ModeleQPIGS qpigs : dataQPIGS) {
                            puissanceAc.add(qpigs.getPuissanceActiveDeSortie_AC());
                            System.out.println(qpigs.getPuissanceActiveDeSortie_AC());
                        }
                        System.out.println("Puissance diff");
                        stockValeurEnvoie = sqlGestion.mesure(puissanceAc ,m.getMac());
                        System.out.println("Mesure effectuer");
                        boolean dataStatue = bddDistante.post(stockValeurEnvoie , m.getIpServeur());
                        if (dataStatue) {
                            System.out.println("envoyer");
                            //mettre l'icone connexion
                    } dataQPIGS.clear();
                } catch (SQLException e) {
                    System.err.println(e + "erreur Sql");
                } catch (IOException e) {
                    System.err.println(e + " erreur connection");;
                }
            }
        };
        bdt2.scheduleAtFixedRate(partieBddCalculs,2000,60000);

}
    public void partiGraphique(){
    TimerTask partieGraph = new TimerTask() {
        @Override
        public void run() {
            try {
                updateGainGraph();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    };
    bdt3.scheduleAtFixedRate(partieGraph,10000 ,60000);
}
    public void recupMesureOnduleur() {
      TimerTask  ordreDemandeOnduleur = new TimerTask() {
            @Override
            public void run() {try {
                wks.demandeQPIRI();
                Thread.sleep(3000);
                wks.demandeQPIWS();
                Thread.sleep(3000);
                wks.demandeQPIGS();
                Thread.sleep(3000);
                } catch (InterruptedException ignored) {}}};
      bdt.scheduleAtFixedRate(ordreDemandeOnduleur,2000,1000);
    }
    public void updateGainGraph() throws SQLException {
        Platform.runLater(()->{try{labelGainM.setText(sqlGestion.getlastMonth());} catch (SQLException ignored){}});
        Platform.runLater(()->{try {labelGainJ.setText(sqlGestion.getlastDay());} catch (SQLException ignored) {}});
        Platform.runLater(()->{try {labelGainH.setText(sqlGestion.getlastHours());} catch (SQLException ignored) {}});
        Platform.runLater(()->{try {labelGainI.setText(String.valueOf(sqlGestion.getLastValue().getEuro()));} catch (SQLException ignored) {}});
        System.out.println("partie graphique a jour");
    }
    public  void lineChartStat(String annee) throws SQLException {
    }
}

