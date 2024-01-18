package org.example.java_bank_app.ControllersPackage;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.java_bank_app.CustomIteratorPackage.CustomListIterator;
import org.example.java_bank_app.LoginGUI;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.SQLPackage.*;
import org.example.java_bank_app.UserClassesPackage.Wallet;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoggedUserController implements Initializable {

    @FXML
    Label UserNameLabel, WalletNameLabel, BalanceLabel, CurrencyLabel;
    User user;
    ObjectProperty<Wallet> actuallWallet;
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

            //Wallets Iterator Area
            walletsIterator = new CustomListIterator<>(user.getWallets());
            walletsIterator.setIteratorChangeListener((currentWallet -> actuallWallet.set((Wallet) currentWallet)));

            //Wallet Area
            actuallWallet = walletsIterator.getCurrentObjectProperty();

            if(!actuallWallet.isNull().get()) setLabels();
            changeLabelsVisibility(!user.getWallets().isEmpty());

            actuallWallet.addListener((observableValue, oldValue, newValue) -> {
                changeLabelsVisibility(newValue != null);
                if(newValue != null) setLabels();
            });
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
        stage.setOnHidden((e -> refreshData()));

        stage.show();
    }

    public void openCurrencyRates() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("currencyRates-view.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setX(600);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();
    }

    @FXML
    public void deleteWallet(){
        if (!actuallWallet.isNull().get()) {
            mySQL_class.deleteWallet(actuallWallet.get());
            refreshData();
        }
    }

    @FXML
    public void refreshData(){
        user.setWallets(mySQL_class.getUserWallets(user));
    }

    //pass variables
    public void passUser(User user){
        this.user = user;
    }

    //private methods
    private void setLabels(){
        WalletNameLabel.setText(actuallWallet.get().getName());
        BalanceLabel.setText(actuallWallet.get().getMoneyAmount().toString());
        CurrencyLabel.setText(actuallWallet.get().getCurrency().getCurrencyCode().toString());
    }

    private void changeLabelsVisibility(boolean visibility){
        WalletNameLabel.setVisible(visibility);
        BalanceLabel.setVisible(visibility);
        CurrencyLabel.setVisible(visibility);
    }


}
