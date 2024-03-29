package org.example.java_bank_app.ControllersPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.AnimationsPackage.CustomAnimations;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;
import org.example.java_bank_app.SQLPackage.mySQL_class;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;


import java.math.BigDecimal;

import java.net.URL;
import java.util.ResourceBundle;




public class AddWalletsController implements Initializable {

    @FXML
    TextField WalletNameTextField, BalanceTextField;
    @FXML
    ComboBox<CurrencyCode> CurrencyCodeComboBox;
    @FXML
    ImageView AddImage, RefreshImage;

    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<CurrencyCode> currencyCodes = FXCollections.observableArrayList(CurrencyCode.values());
        CurrencyCodeComboBox.setItems(currencyCodes);
        CurrencyCodeComboBox.setValue(CurrencyCode.PLN);
        CurrencyCodeComboBox.setVisibleRowCount(3);

        CustomAnimations.scaleOnMousePress(AddImage, RefreshImage);
        CustomAnimations.glowOnMouseEnter(Color.GOLD, AddImage, RefreshImage);

    }

    public void passUser(User user){
        this.user = user;
    }

    @FXML
    public void addWallet(){

        boolean isInputEmpty = WalletNameTextField.getText().isEmpty() || BalanceTextField.getText().isEmpty();
        boolean isBalanceValid = BalanceTextField.getText().matches("\\d+(\\.\\d{1,2})?");
        boolean isNameTaken = mySQL_class.getUserWallets(user).stream().anyMatch(wallet -> wallet.getName().equals(WalletNameTextField.getText()));

        if(!isBalanceValid || isInputEmpty || isNameTaken) CustomAlert.showInfoAlert("Wrong input/name taken");
        else {
            CurrencyCode currencyCode = CurrencyCodeComboBox.getValue();
            String walletName = WalletNameTextField.getText();
            BigDecimal balance = new BigDecimal(BalanceTextField.getText());
            mySQL_class.addWallet(user, balance, currencyCode, walletName);
            CustomAlert.showInfoAlert("Wallet added succesfully!");
        }
    }

    @FXML
    public void refreshInput(){
        CurrencyCodeComboBox.setValue(CurrencyCode.PLN);
        WalletNameTextField.setText("");
        BalanceTextField.setText("");
    }

}
