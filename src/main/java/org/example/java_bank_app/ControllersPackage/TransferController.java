package org.example.java_bank_app.ControllersPackage;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.java_bank_app.TransactionsPackage.Transaction;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;
import org.example.java_bank_app.SQLPackage.mySQL_class;
import org.example.java_bank_app.AlertPackage.CustomAlert;


import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
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
    public Wallet helperwallet;
    private LoggedUserController controller;

    ObjectProperty<Wallet> actuallWallet;
    User user;
    //quickfix podpowiada żeby zmienić na lokalne ale musze z nich zrobić zpaytanie do SQL i jeszcze nie wiem jak
    //więc mam je tu żeby o nich pamietać gdybym je przesyłał do innej klasy pdw.

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
    public void passActuallWallet(ObjectProperty<Wallet> actuallWallet) { this.actuallWallet = actuallWallet; }
    public void onTransferButtonClick(ActionEvent event) throws SQLException {
        BigDecimal int_transfer_amount = BigDecimal.valueOf(Double.parseDouble(transfer_amount.getText()));
        String string_target_username = target_user.getText();

        //Teraz -> wysłać zapytanie do sql
        //Transaction type temporary
        //Transaction type wywalilem z bazy danych, bo dopiero na podstawie tego czy jesteś odbiorcą czy wysyłajacym
        //w historii, będę mógł ustalić czy type jest send czy receive



        if(mySQL_class.isUsernameAvaliable(string_target_username)){
            if(int_transfer_amount.doubleValue() <= wallet_box.getValue().getMoneyAmount().doubleValue()) {
                    ObservableList<Wallet> wallets = mySQL_class.getUserWallets_byid(mySQL_class.find_user_id_by_nickname(string_target_username));

                    //pętla do zmiany - narazie działa
                    for(Wallet wallet: wallets){
                        if(wallet.getCurrency().getCurrencyCode()== wallet_box.getValue().getCurrency().getCurrencyCode()){
                            helperwallet = wallet;
                            break;
                        }
                    }
                    if (helperwallet != null) {
                        Transaction transaction = new Transaction(wallet_box.getValue(), helperwallet, int_transfer_amount);
                        System.out.println(transaction);
                        mySQL_class.makeTransaction(transaction);
                        wallet_box.setItems(user.getWallets());


                        CustomAlert.showInfoAlert("money has been sent :)");


                        Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
                        currentStage.close();

                    }
                    else{
                        CustomAlert.showInfoAlert("user dont have wallet in your currency, transaction is cancelled.");
                    }
            }
            else{
                CustomAlert.showInfoAlert("you don't have enough money");
            }
        }
        else{
            CustomAlert.showInfoAlert("target not existing");
        }

    }

}
