package com.example.interfaceonduleurv0.controller;

import com.example.interfaceonduleurv0.modeles.ModeleConfiguration;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfigControleur implements Initializable {
    public Button bt_valider;
    public Button bt_annuler;
    public TextField tf_latitude;
    public TextField tf_longitude;
    public TextField tf_mac;
    public TextField tf_serveur;

    private Stage configStage;
    private ModeleConfiguration modeleConfiguration;
    private String regex = "[+-]?([0-9]*[.,])?[0-9]+";
    private String regexUrl = "https?:\\/\\/(www\\\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}[\\.[a-zA-Z0-9()]{1,6}\b]?([-a-zA-Z0-9()!@:%_\\+.~#?&\\/\\/=]*)";

    private File fichier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Platform.runLater(()->{tf_mac.setText(trouverMac());});
            bt_annuler.setOnAction(this::ev_bt_annuler);
            bt_valider.setOnAction(this::ev_bt_valider);
            fichier = new File("./config.bin");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));
            ModeleConfiguration m = (ModeleConfiguration) ois.readObject();
            System.out.println(trouverMac());
            Platform.runLater(()->{ tf_latitude.setText(m.getLatitude());});
            Platform.runLater(()->{ tf_longitude.setText(m.getLongitude());});
            Platform.runLater(()->{  tf_serveur.setText(m.getIpServeur());});


        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setConfigStage(Stage configStage) {
        this.configStage = configStage;
    }

    public void ev_bt_valider(ActionEvent actionEvent)  {
        if ((tf_latitude.getText().matches(regex)) && (tf_longitude.getText().matches(regex) && (tf_serveur.getText().matches(regexUrl)))) {
            modeleConfiguration = new ModeleConfiguration(tf_mac.getText(), tf_latitude.getText(), tf_longitude.getText(), tf_serveur.getText());

            fichier = new File("./config.bin");
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier));
                oos.writeObject(modeleConfiguration);
                System.out.println(modeleConfiguration.getLatitude() + "modele sur le flux");
                oos.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            this.configStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur de saisie");
            if (!(tf_latitude.getText().matches(regex)) || !(tf_longitude.getText().matches(regex))) {
                alert.setContentText("format latitude et longitude: nombre!");
            }else {
                alert.setContentText("format @Serveur : url!");
            }
            alert.showAndWait();
        }
    }

    public void ev_bt_annuler(ActionEvent actionEvent) {
        this.configStage.close();
    }
    public String trouverMac() {
        String mac = "?";
        //String homeDir = System.getenv("HOME");
        //String[] cmd = {homeDir + "/IdeaProjects/onduleur_fxml_no_lora/build/libs/determineMacWifi.sh"};
        String[] cmd = {"./determineMacEth.sh"};
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader reponse = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder stringBuilderMac = new StringBuilder();
            String line;
            while ((line = reponse.readLine()) != null) {
                stringBuilderMac.append(line);
            }
            mac = stringBuilderMac.toString();
            System.out.println("mac= " + mac);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //return mac.toLowerCase();
        System.out.println(mac);
        return mac;
    }


}
