package org.example.java_bank_app.ControllersPackage;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.AnimationsPackage.CustomAnimations;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;
import org.example.java_bank_app.SQLPackage.mySQL_class;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangeWalletNameController implements Initializable {

    public Label change_label;
    public TextField name_field;
    public Group change_button;
    public Label newwalletname_label;
    private User user;
    private ObjectProperty<Wallet> actuallWallet;

    public void passUser(User user){
        this.user = user;
    }
    public void passActuallWallet(ObjectProperty<Wallet> actuallWallet) { this.actuallWallet = actuallWallet; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newwalletname_label.getStyleClass().add("change-text");

        change_label.setStyle("-fx-text-fill: black");
        CustomAnimations.glowOnMouseEnter(Color.GOLD, change_button);
        Platform.runLater(() -> {
        });

    }
    public void changeName(MouseEvent event) throws SQLException {
        boolean isInputEmpty = name_field.getText().isEmpty();
        boolean isNameTaken = mySQL_class.getUserWallets(user).stream().anyMatch(wallet -> wallet.getName().equals(name_field.getText()));


        if(isInputEmpty || isNameTaken) CustomAlert.showInfoAlert("wrong input/name taken");
        else {
         //code
            mySQL_class.ChangeWalletName(actuallWallet.getValue().getId(), name_field.getText());
            CustomAlert.showInfoAlert("Name changed");
            System.out.println(actuallWallet.getValue().getName());

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }
    }
}
