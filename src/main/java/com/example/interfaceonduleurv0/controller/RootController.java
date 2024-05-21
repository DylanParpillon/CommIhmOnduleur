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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jssc.SerialPortException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Dydou P
 * Cette classe est responsable de contrôler l'interface utilisateur de l'application,
 * gérant les interactions avec l'utilisateur et les opérations nécessaires pour récupérer,
 * afficher et enregistrer les données.
 * Elle implémente l'interface Initializable de JavaFX pour initialiser les composants de l'interface.
 */
public class RootController implements Initializable {
    public CategoryAxis bottomLine;
    public NumberAxis topLine;
    public Label labelEnvoie;
    public RadioButton buttonUtilisation;
    public RadioButton buttonPS;
    public RadioButton buttonBatterie;
    Ihm ihm;

    public void setMainApp(Ihm ihm) {
        this.ihm = ihm;
    }

    /**
     * Référence à la base de données distante.
     */
    public BddDistante bddDistante = new BddDistante();

    /**
     * Bouton de contrôle.
     */
    public Button buttonId;

    /**
     * Disposition principale de l'interface.
     */
    public VBox mainLayout;

    /**
     * Labels pour afficher les données.
     */
    public Label labelBatterie;
    public Label labelPhotoEntrer;
    public Label labelPSortie;
    public Label labelTensionSortie;
    public Label labelStatut;
    public Label labelGainI;
    public Label labelGainH;
    public Label labelGainM;
    public Label labelGainJ;

    /**
     * Choix de la date.
     */
    public ChoiceBox choiceBoxDate;

    /**
     * Graphique de ligne.
     */
    public LineChart chartId;
    public Button buttonServer;
    public Button ButtonSetting;

    /**
     * Timer pour les tâches périodiques.
     */
    Timer bdt = new Timer();
    Timer bdt2 = new Timer();
    Timer bdt3 = new Timer();
    Timer bdt4 = new Timer();

    /**
     * Liste pour stocker les données QPIGS.
     */
    public ArrayList<ModeleQPIGS> dataQPIGS = new ArrayList<>();

    /**
     * Liste pour stocker les données QPIRI.
     */
    public ArrayList<ModeleQPIRI> dataQPIRI = new ArrayList<>();

    /**
     * Liste pour stocker les données QPIWS.
     */
    public ArrayList<ModeleQPIWS> dataQPIWS = new ArrayList<>();

    /**
     * Chaîne pour sauvegarder des tests.
     */
    String saveTest;
    SqlGestion sqlGestion = new SqlGestion();
    StringBuilder texteInfo = new StringBuilder("----Info Onduleur---- \n\n");

    /**
     * Liste pour stocker les valeurs à envoyer.
     */
    ModeleData stockValeurEnvoie = new ModeleData();
    ArrayList<ModeleData> saveOffline = new ArrayList<>();

    /**
     * Instance de la classe Wks pour la communication avec le matériel.
     */
    Wks wks = new Wks(this);
    String values = "02";

    /**
     * Modèle pour les données QPIGS.
     */
    ModeleQPIGS modeleQPIGS = new ModeleQPIGS();

    /**
     * Constructeur par défaut.
     *
     * @throws SQLException si une erreur SQL se produit
     */
    public RootController() throws SQLException {
    }
    ModeleConfiguration m;
    String com ;

    /**
     * Méthode d'initialisation de la classe RootController.
     *
     * @param location  URL de localisation (non utilisé dans cette implémentation)
     * @param resources ResourceBundle (non utilisé dans cette implémentation)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       com = new LiaisonSerie().listerLesPorts().toString();
        buttonBatterie.setSelected(true);
            buttonBatterie.setOnAction((e)->{
                values = "02";
                buttonPS.setSelected(false);
                buttonUtilisation.setSelected(false);
            });
           buttonPS.setOnAction((e)->{
               values ="01" ;
               buttonBatterie.setSelected(false);
               buttonUtilisation.setSelected(false);
           });
           buttonUtilisation.setOnAction((e)->{
               values ="00";
             buttonPS.setSelected(false);
               buttonBatterie.setSelected(false);
           });
        ButtonSetting.setOnAction(e -> {
            ihm.configView();
            try {
                Thread.sleep(1000);
                File fichier = new File("./config.bin");
                boolean flag = true;
                while (flag) {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));
                    m = (ModeleConfiguration) ois.readObject();
                    System.out.println(m.getLatitude());
                    if (m != null) {
                        flag = false;
                        texteInfo.append("-MacAdresse : "+ m.getMac());
                        bddDistante.insertInverter(initialisation(), m.getIpServeur());
                        Platform.runLater(()->{labelEnvoie.setTextFill(Color.GREEN);labelEnvoie.setText("ATTENTION APPAREIL CONFIGURER");});
                        modifWarning();
                    }
                }
            } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        });
        buttonServer.setOnAction((e) -> {
            ihm.wifiView();
        });
        try {
            ObservableList<String> ObList = FXCollections.observableList(sqlGestion.getAllDate());
            choiceBoxDate.setItems(ObList);
            choiceBoxDate.setOnAction(event -> {
                try {
                    System.out.println(choiceBoxDate.getValue());
                    lineChartStat(choiceBoxDate.getValue().toString());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            wks.initCom(new LiaisonSerie().listerLesPorts().get(0).toString());
            wks.configurerParametres(2400, 8, 0, 1);
            recupMesureOnduleur();
        } catch (SerialPortException e) {
            System.err.println(e + " serialport");
        } catch (SQLException e) {
            System.err.println(e + " sql erreur");
        }
        partiGraphique();
        TimerTask partieBddCalculs = new TimerTask() {
            @Override
            public void run() {
                try {
                    ArrayList<String> puissanceAc = new ArrayList<>();
                    for (ModeleQPIGS qpigs : dataQPIGS) {
                        puissanceAc.add(qpigs.getPuissanceActiveDeSortie_AC());
                        System.out.println(qpigs.getPuissanceActiveDeSortie_AC());
                    }
                    stockValeurEnvoie = sqlGestion.mesure(puissanceAc);
                    System.out.println("Mesure passé");
                    if (m != null) {
                        stockValeurEnvoie.setMacAddress(m.getMac());
                        boolean dataStatue = bddDistante.post(stockValeurEnvoie, m.getIpServeur());
                        if (dataStatue) {
                            System.out.println("envoyer");
                            Platform.runLater(()->{labelEnvoie.setTextFill(Color.GREEN);
                            labelEnvoie.setText("Données envoyer à distance");});
                            saveOffline.clear();
                            //mettre l'icone connexion
                        } else {
                            System.out.println("pas envoyer");
                            Platform.runLater(()->{labelEnvoie.setText("Données non envoyer");  });
                            saveOffline.add(stockValeurEnvoie);
                        }
                    }else {
                        Platform.runLater(()->{
                            labelEnvoie.setTextFill(Color.RED);
                            labelEnvoie.setText("ATTENTION APPAREIL NON CONFIGURER");
                        });
                    }
                    dataQPIGS.clear();
                } catch (SQLException e) {
                    System.err.println(e + "erreur Sql");
                } catch (IOException e) {
                    System.err.println(e + " erreur connection");
                }
            }
        };
        bdt2.scheduleAtFixedRate(partieBddCalculs, 2000, 60000);

    }

    public void partiGraphique() {
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
        bdt3.scheduleAtFixedRate(partieGraph, 10000, 60000);
    }

    public void recupMesureOnduleur() {
        TimerTask ordreDemandeOnduleur = new TimerTask() {
            @Override
            public void run() {
                try {
                    wks.demandeQPIRI(values);
                    Thread.sleep(6000);
                    wks.demandeQPIWS();
                    Thread.sleep(6000);
                    wks.demandeQPIGS();
                    Thread.sleep(6000);
                } catch (InterruptedException ignored) {
                }
            }
        };
        bdt.scheduleAtFixedRate(ordreDemandeOnduleur, 2000, 2000);
    }

    public void updateGainGraph() throws SQLException {
        Platform.runLater(() -> {
            try {
                labelGainM.setText(sqlGestion.getlastMonth());
            } catch (SQLException ignored) {
            }
        });
        Platform.runLater(() -> {
            try {
                labelGainJ.setText(sqlGestion.getlastDay());
            } catch (SQLException ignored) {
            }
        });
        Platform.runLater(() -> {
            try {
                labelGainH.setText(sqlGestion.getlastHours());
            } catch (SQLException ignored) {
            }
        });
        Platform.runLater(() -> {
            try {
                labelGainI.setText(String.valueOf(sqlGestion.getLastValue().getEuro()));
            } catch (SQLException ignored) {
            }
        });
        Platform.runLater(() -> {
            labelStatut.setText(String.valueOf(texteInfo));
        });
        Platform.runLater(() -> {
            labelStatut.setText(com);
        });
        System.out.println("partie graphique a jour");
    }

    public void lineChartStat(String annee) throws SQLException {
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Nombre de Mois");
        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Valeur");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("hello");
        //populating the series with data
        series.getData().add(new XYChart.Data<>(1, sqlGestion.getStatMonthOnYears(annee, "01")));
        series.getData().add(new XYChart.Data(2, sqlGestion.getStatMonthOnYears(annee, "02")));
        series.getData().add(new XYChart.Data(3, sqlGestion.getStatMonthOnYears(annee, "03")));
        series.getData().add(new XYChart.Data(4, sqlGestion.getStatMonthOnYears(annee, "04")));
        series.getData().add(new XYChart.Data(5, sqlGestion.getStatMonthOnYears(annee, "05")));
        series.getData().add(new XYChart.Data(6, sqlGestion.getStatMonthOnYears(annee, "06")));
        series.getData().add(new XYChart.Data(7, sqlGestion.getStatMonthOnYears(annee, "07")));
        series.getData().add(new XYChart.Data(8, sqlGestion.getStatMonthOnYears(annee, "08")));
        series.getData().add(new XYChart.Data(9, sqlGestion.getStatMonthOnYears(annee, "09")));
        series.getData().add(new XYChart.Data(10, sqlGestion.getStatMonthOnYears(annee, "10")));
        series.getData().add(new XYChart.Data(11, sqlGestion.getStatMonthOnYears(annee, "11")));
        series.getData().add(new XYChart.Data(12, sqlGestion.getStatMonthOnYears(annee, "12")));
        lineChart.getData().add(series);
        System.out.println("mesure fait");
    }

    public ModeleInsert initialisation() {
        ModeleQPIGS lastQ = new ModeleQPIGS();
        for (ModeleQPIGS m : dataQPIGS) lastQ = m;
        ModeleInsert start = new ModeleInsert();
            start.setName(generateRandomString(5));
            start.setMacAddress(m.getMac());
            start.setPosition(m.getLatitude(), m.getLongitude());
            start.setIsOnline(true);
        if(lastQ != null )  start.setBatteryPercentage(Integer.parseInt(lastQ.getPourcentageCapaciteBatterie()));
            start.setOutputActivePower(0);
            start.setOutputVoltage(12);
            start.setInverterFault(false);
            start.setLineFail(false);
            start.setVoltageTooLow(false);
            start.setVoltageTooHigh(false);
            start.setOverTemperature(false);
            start.setFanLocked(false);
            start.setBatteryLowAlarm(false);
            start.setBatteryTooLowToCharge(false);
            start.setSoftFail(false);
        return start;
    }

    public void modifWarning(){

        TimerTask updateWarning = new TimerTask() {
            @Override
            public void run() {
                ModeleWarning modeleWarning = new ModeleWarning(); ModeleQPIWS lastW = new ModeleQPIWS();
                for (ModeleQPIWS w : dataQPIWS) lastW = w;
                modeleWarning.setMacAddress(m.getMac());
                modeleWarning.setInverterFault(lastW.getDefaillanceOnduleur().equals("true"));
                modeleWarning.setLineFail(lastW.getLineFail().equals("true"));
                modeleWarning.setVoltageTooLow(lastW.getTensionOnduleurTropFaible().equals("true"));
                modeleWarning.setVoltageTooHigh(lastW.getTensionOnduleurTropElevee().equals("true"));
                modeleWarning.setOverTemperature(lastW.getSurchauffe().equals("true"));
                modeleWarning.setFanLocked(lastW.getVentilateurVerrouille().equals("true"));
                modeleWarning.setBatteryLowAlarm(lastW.getAlarmeBatterieFaible().equals("true"));
                modeleWarning.setSoftFail(lastW.getBatterieTropFaiblePourCharger2().equals("true")||lastW.getBatterieTropFaiblePourEtreChargee1().equals("true")||lastW.getBatterieTropFaiblePourEtreChargee3().equals("true"));
                modeleWarning.setBatteryTooLowToCharge(lastW.getOnduleurSoftFail().equals("true"));
                bddDistante.modifWarning(modeleWarning,m.getIpServeur());
            }
        };
        bdt4.scheduleAtFixedRate(updateWarning,0,60000);
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder("onduleur-");
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }


}

