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
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.AnimationsPackage.CustomAnimations;
import org.example.java_bank_app.CustomIteratorPackage.CustomListIterator;
import org.example.java_bank_app.LoginGUI;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.SQLPackage.*;
import org.example.java_bank_app.UserClassesPackage.Wallet;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoggedUserController implements Initializable {

    @FXML
    Label UserNameLabel, WalletNameLabel, BalanceLabel, CurrencyLabel, StatusLabel;

    @FXML
    Group TransferMoneyButton, TransactionHistoryButton, CurrencyRatesButton, ShowWalletsButton;

    @FXML
    ImageView NextImage, PreviousImage, AddWalletImage, DeleteWalletImage, RefreshImage, OptionsImage, LogoutImage;

    Stage loginStage, currentStage;

    User user;
    ObjectProperty<Wallet> actuallWallet;
    CustomListIterator<Wallet> walletsIterator;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UserNameLabel.setAlignment(Pos.CENTER);
        WalletNameLabel.setAlignment(Pos.CENTER);
        StatusLabel.setAlignment(Pos.CENTER);

        CustomAnimations.glowOnMouseEnter(
                Color.GOLD,
                NextImage, PreviousImage, AddWalletImage, DeleteWalletImage,
                RefreshImage, OptionsImage, LogoutImage
        );

        CustomAnimations.glowOnMouseEnter(
                Color.BLACK,
                TransferMoneyButton, TransactionHistoryButton, CurrencyRatesButton, ShowWalletsButton
        );

        CustomAnimations.scaleOnMousePress(
                NextImage, PreviousImage, AddWalletImage, DeleteWalletImage,
                RefreshImage, OptionsImage, LogoutImage, TransferMoneyButton,
                TransactionHistoryButton, CurrencyRatesButton, ShowWalletsButton
        );




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
            else StatusLabel.setText("");
            changeLabelsVisibility(!user.getWallets().isEmpty());

            actuallWallet.addListener((observableValue, oldValue, newValue) -> {
                System.out.println(newValue);
                changeLabelsVisibility(newValue != null);
                if(newValue != null) setLabels();
            });
        });

    }

    @FXML
    public void nextWallet(){
        System.out.println(actuallWallet);
        walletsIterator.getNext();
    }

    @FXML
    public void previousWallet(){
        walletsIterator.getPrevious();
    }

    @FXML
    public void openAddWallets() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("addWallets-view.fxml"));
        File cssFile = new File("src/main/resources/org/example/java_bank_app/addWallets-style.css");

        Parent root = fxmlLoader.load();

        AddWalletsController addWalletsController = fxmlLoader.getController();
        addWalletsController.passUser(user);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());
        stage.setTitle("AK Bank");

        stage.setScene(scene);
        stage.setX(600);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnHidden((e -> refreshData()));

        stage.show();
    }

    @FXML
    public void openCurrencyRates() throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("currencyRates-view.fxml"));
        File cssFile = new File("src/main/resources/org/example/java_bank_app/currencyRates-style.css");

        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());

        stage.setTitle("AK Bank");
        stage.setScene(scene);
        stage.setX(600);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnHidden((e -> refreshData()));

        stage.show();
    }

    @FXML
    public void openTransferWindow() throws IOException{
        if(!user.getWallets().isEmpty()){
            FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("transfer-view.fxml"));
            File cssFile = new File("src/main/resources/org/example/java_bank_app/transfer_view.css");
            Parent root = fxmlLoader.load();

            TransferController transferController = fxmlLoader.getController();
            transferController.passUser(user);
            transferController.passActuallWallet(actuallWallet);


            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("AK Bank");

            scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());

            stage.setScene(scene);
            stage.setX(600);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden((e -> refreshData()));

            stage.show();
        }else CustomAlert.showInfoAlert("User has no wallets");
    }

    @FXML
    public void openTransactionsHistory() throws IOException{

        if(!user.getWallets().isEmpty()){
            FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("transactionsHistory-view.fxml"));
            File cssFile = new File("src/main/resources/org/example/java_bank_app/transactionsHistory-style.css");
            Parent root = fxmlLoader.load();

            TransactionsHistoryController transferController = fxmlLoader.getController();
            transferController.passUser(user);

            Stage stage = new Stage();
            stage.setTitle("AK Bank");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());
            stage.setScene(scene);


            stage.setX(600);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden((e -> refreshData()));

            stage.show();
        }else CustomAlert.showInfoAlert("User has no wallets");

    }

    @FXML
    public void openShowWallets() throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("showwallets-view.fxml"));
            File cssFile = new File("src/main/resources/org/example/java_bank_app/showwallets_style.css");
            Parent root = fxmlLoader.load();

            ShowWalletsController showWalletsController = fxmlLoader.getController();
            showWalletsController.passUser(user);

            Stage stage = new Stage();
            stage.setTitle("AK Bank");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());

            stage.setScene(scene);

            stage.setX(600);
            stage.setOnHidden((e -> refreshData()));

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
        if(!actuallWallet.isNull().get()) setLabels();
        changeLabelsVisibility(!actuallWallet.isNull().get());
    }

    @FXML
    public void logout() {
        currentStage.close();
        loginStage.show();
    }

    //pass variables
    public void passUser(User user){
        this.user = user;
    }
    public void passLoginStage(Stage stage) { this.loginStage = stage; }
    public void passStage(Stage stage) { this.currentStage = stage; }


    //private methods
    private void setLabels(){
        StatusLabel.setText(actuallWallet.get().getStatus().toString());
        WalletNameLabel.setText(actuallWallet.get().getName());
        BalanceLabel.setText(actuallWallet.get().getMoneyAmount().toString());
        CurrencyLabel.setText(actuallWallet.get().getCurrency().getCurrencyCode().toString());
    }

    private void changeLabelsVisibility(boolean visibility){
        WalletNameLabel.setVisible(visibility);
        BalanceLabel.setVisible(visibility);
        CurrencyLabel.setVisible(visibility);
        StatusLabel.setVisible(visibility);
    }



}
