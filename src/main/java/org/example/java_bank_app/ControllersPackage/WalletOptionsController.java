package org.example.java_bank_app.ControllersPackage;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.AnimationsPackage.CustomAnimations;
import org.example.java_bank_app.LoginGUI;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;
import org.example.java_bank_app.UserClassesPackage.Status;




public class WalletOptionsController implements Initializable {
    public Group name_group;
    public Group status_group;
    public Group currency_group;

    protected User user;
    protected ObjectProperty<Wallet> actuallWallet;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomAnimations.glowOnMouseEnter(Color.GOLD,name_group,status_group,currency_group);
        Platform.runLater(() -> {
        });
    }

    public void passUser(User user){
        this.user = user;
    }
    public void passActuallWallet(ObjectProperty<Wallet> actuallWallet) { this.actuallWallet = actuallWallet; }

    public void onWalletNamebuttonClick() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("ChangeWalletName-view.fxml"));
            File cssFile = new File("src/main/resources/org/example/java_bank_app/C_WalletOptions_style.css");
            Parent root = fxmlLoader.load();

            ChangeWalletNameController changeWalletNameController = fxmlLoader.getController();
            changeWalletNameController.passUser(user);
            changeWalletNameController.passActuallWallet(actuallWallet);


            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Wallet change name");

            scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());

            stage.setScene(scene);
            stage.setX(600);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }

    public void onStatusbuttonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("ChangeWalletStatus-view.fxml"));
        File cssFile = new File("src/main/resources/org/example/java_bank_app/C_WalletOptions_style.css");
        Parent root = fxmlLoader.load();

        ChangeWalletStatusController changeWalletStatusController = fxmlLoader.getController();
        changeWalletStatusController.passUser(user);
        changeWalletStatusController.passActuallWallet(actuallWallet);


        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Wallet status change");

        scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());

        stage.setScene(scene);
        stage.setX(600);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
    public void onCurrencybuttonclick(){

    }
}


