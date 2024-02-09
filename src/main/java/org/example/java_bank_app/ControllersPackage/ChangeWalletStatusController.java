package org.example.java_bank_app.ControllersPackage;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.AnimationsPackage.CustomAnimations;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;
import org.example.java_bank_app.SQLPackage.mySQL_class;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeWalletStatusController implements Initializable {

    public ChoiceBox<String> choicebox;
    public Group apply_button;
    private User user;
    private ObjectProperty<Wallet> actuallWallet;

    public void passUser(User user){
        this.user = user;
    }
    public void passActuallWallet(ObjectProperty<Wallet> actuallWallet) { this.actuallWallet = actuallWallet; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CustomAnimations.glowOnMouseEnter(
                Color.GOLD,
                apply_button
        );

        CustomAnimations.scaleOnMousePress(apply_button);



        Platform.runLater(() -> {
            ObservableList<String> status_list = FXCollections.observableArrayList();
            status_list.add("ACTIVE");
            status_list.add("DELETED");
            status_list.add("INACTIVE");

            choicebox.setItems(status_list);
            choicebox.setValue(actuallWallet.getValue().getStatus().toString());
            System.out.println(actuallWallet);
        });

    }

    public void onApply_buttonClick(MouseEvent e){
        mySQL_class.changeWalletStatus(actuallWallet,choicebox.getValue());
        CustomAlert.showInfoAlert("Wallet status changed");
    }
}
