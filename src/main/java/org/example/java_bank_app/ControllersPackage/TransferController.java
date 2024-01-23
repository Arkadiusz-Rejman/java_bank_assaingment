package org.example.java_bank_app.ControllersPackage;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;

import java.net.URL;
import java.util.ResourceBundle;

public class TransferController implements Initializable {


    @FXML
    public TextField transfer_amount, target_user;
    @FXML
    public ChoiceBox<Wallet> wallet_box;
    @FXML
    public Label balance_label;
    @FXML
    public Button transfer_button;

    ObjectProperty<Wallet> actuallWallet;
    User user;
    //quickfix podpowiada żeby zmienić na lokalne ale musze z nich zrobić zpaytanie do SQL i jeszcze nie wiem jak
    //więc mam je tu żeby o nich pamietać gdybym je przesyłał do innej klasy pdw.
    private int int_transfer_amount;
    private String string_target_username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            wallet_box.setItems(user.getWallets());
            wallet_box.setValue(actuallWallet.getValue());
            balance_label.setText("Current balance is: "+ wallet_box.getValue().getMoneyAmount()+" "+wallet_box.getValue().getCurrency().getCurrencyCode());
            System.out.println(actuallWallet);
            wallet_box.valueProperty().addListener((observable, oldValue, newValue) -> balance_label.setText("Current balance is: "+ wallet_box.getValue().getMoneyAmount()+" "+wallet_box.getValue().getCurrency().getCurrencyCode()));
    });
    }

    public void passUser(User user){
        this.user = user;
    }
    public void passActuallWallet(ObjectProperty<Wallet> actuallWallet) {
        this.actuallWallet = actuallWallet;
    }
    public void onTransferButtonClick(){
        int_transfer_amount = Integer.parseInt(transfer_amount.getText());
        string_target_username = target_user.getText();
        //Teraz -> wysłać zapytanie do sql
    }



}
