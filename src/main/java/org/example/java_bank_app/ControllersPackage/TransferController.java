package org.example.java_bank_app.ControllersPackage;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;
import org.example.java_bank_app.ControllersPackage.LoggedUserController;
import org.example.java_bank_app.LoginGUI;
import  org.example.java_bank_app.SQLPackage.mySQL_class;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TransferController implements Initializable {


    public TextField transfer_amount;
    public TextField target_user;
    @FXML
    public ChoiceBox<String> wallet_box;
    public Label balance_label;


    User user;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            wallet_box.setItems(mySQL_class.getWalletNamesForUser(user.getId()));
            wallet_box.setValue(mySQL_class.getWalletNamesForUser(user.getId()).getFirst());

            balance_label.setText("Current balance: " + mySQL_class.getWalletBalance(user.getId(), wallet_box.getValue()));
            wallet_box.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                    balance_label.setText("Current balance: " + mySQL_class.getWalletBalance(user.getId(), wallet_box.getValue()));
                }
        });

    });
    }

    public void passUser(User user){
        this.user = user;
        System.out.println();

    }
    public void setbalanceLabel(){


    }


}
