package org.example.java_bank_app.ControllersPackage;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;
import org.example.java_bank_app.CustomIteratorPackage.CustomListIterator;
import org.example.java_bank_app.LoginGUI;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.SQLPackage.*;
import org.example.java_bank_app.UserClassesPackage.Wallet;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class LoggedUserController implements Initializable {

    @FXML
    Label UserNameLabel, WalletNameLabel, BalanceLabel, CurrencyLabel;
    User user;
    Wallet actuallWallet;
    CustomListIterator<Wallet> walletsIterator;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UserNameLabel.setAlignment(Pos.CENTER);
        WalletNameLabel.setAlignment(Pos.CENTER);
        BalanceLabel.setAlignment(Pos.CENTER);
        CurrencyLabel.setAlignment(Pos.CENTER);



        //Platform runLater musi być używany podczas gdy korzystamy ze zmiennej z innej klasy.
        //Dzieje się tak, gdyż blok inicjalizacyjny jest wykonywany przed przekazaniem zmiennej

        Platform.runLater(() -> {
            //User Area
            UserNameLabel.setText(user.getUsername());

            //Wallet Area
            if(!user.getWallets().isEmpty()){
                actuallWallet = user.getWallets().get(0);
                WalletNameLabel.setText(actuallWallet.getName());
                BalanceLabel.setText(actuallWallet.getMoneyAmount().toString());
                CurrencyLabel.setText(actuallWallet.getCurrency().getCurrencyCode().toString());
            }else{
                WalletNameLabel.setText("No wallets");
            }

            //Wallets Iterator Area
            walletsIterator = new CustomListIterator<>(user.getWallets());
            walletsIterator.setChangeListener((currentWallet ->{
                actuallWallet = (Wallet) currentWallet;
                WalletNameLabel.setText(actuallWallet.getName());
                BalanceLabel.setText(actuallWallet.getMoneyAmount().toString());
                CurrencyLabel.setText(actuallWallet.getCurrency().getCurrencyCode().toString());
            }));
        });

    }


    @FXML
    public void nextWallet(){
        walletsIterator.getNext();
    }

    @FXML
    public void previousWallet(){
        walletsIterator.getPrevious();
    }

    @FXML
    public void openAddWallets() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("addWallets-view.fxml"));
        Parent root = fxmlLoader.load();

        AddWalletsController addWalletsController = fxmlLoader.getController();
        addWalletsController.passUser(user);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setX(600);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setOnHidden((e -> {
            refreshData();
        }));


        stage.show();
    }

    @FXML
    public void refreshData(){
        user.setWallets(mySQL_class.getUserWallets(user));
        walletsIterator.updateList(user.getWallets());
        //Powinien poinformować jeżeli dodano jakiś portfel
    }



    //pass variables
    public void passUser(User user){
        this.user = user;
    }


}
