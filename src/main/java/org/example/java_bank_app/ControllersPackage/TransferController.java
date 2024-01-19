package org.example.java_bank_app.ControllersPackage;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;
import org.example.java_bank_app.SQLPackage.mySQL_class;

import java.net.URL;
import java.util.ResourceBundle;

public class TransferController implements Initializable {


    @FXML
    public TextField transfer_amount, target_user;
    @FXML
    public ChoiceBox<Wallet> wallet_box;
    @FXML
    public Label balance_label;


    User user;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            wallet_box.setItems(user.getWallets());
            wallet_box.setValue(wallet_box.getItems().getFirst());
            balance_label.setText("Current balance is: "+ wallet_box.getValue().getMoneyAmount()+" "+wallet_box.getValue().getCurrency().getCurrencyCode());

            wallet_box.valueProperty().addListener((observable, oldValue, newValue) -> balance_label.setText("Current balance is: "+ wallet_box.getValue().getMoneyAmount()+" "+wallet_box.getValue().getCurrency().getCurrencyCode()));

    });
    }

    public void passUser(User user){
        this.user = user;
        System.out.println();

    }


}
