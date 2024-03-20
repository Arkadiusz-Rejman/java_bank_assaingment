package org.example.java_bank_app.ControllersPackage;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.java_bank_app.AnimationsPackage.CustomAnimations;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowWalletsController implements Initializable {
    public ListView<Wallet> wallet_list;
    public ImageView return_button;
    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomAnimations.glowOnMouseEnter(Color.GOLD, return_button);


        Platform.runLater(() -> {
            ObservableList<Wallet> wallets = user.getWallets();
            wallet_list.getItems().addAll(wallets);
            wallet_list.getSelectionModel().clearSelection();

            // Ustawianie cellFactory dla ListView
            wallet_list.setCellFactory(new Callback<ListView<Wallet>, ListCell<Wallet>>() {
                private boolean isYellow = false;

                @Override
                public ListCell<Wallet> call(ListView<Wallet> listView) {
                    return new ListCell<Wallet>() {
                        @Override
                        protected void updateItem(Wallet wallet, boolean empty) {
                            super.updateItem(wallet, empty);

                            if (empty || wallet == null) {
                                setText(null);
                                setStyle("-fx-background-color: gold;");
                            } else {
                                setText(wallet.toString());
                                if (isYellow) {
                                    setStyle("-fx-background-color: gold;");
                                } else {
                                    setStyle("-fx-background-color: black; -fx-text-fill: gold;");
                                }

                                isYellow = !isYellow;
                            }
                        }
                    };
                }
            });

            // Ustawienie prefHeight
            if (wallet_list.getPrefHeight() >= 300) {
                wallet_list.setPrefHeight(wallets.size() * 25);
            }
        });
    }

    public void passUser(User user){
        this.user = user;
    }
    public void returnonbuttonclick(MouseEvent event){
        Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    @FXML
    private void handleMouseClicked(MouseEvent event) {
        wallet_list.setSelectionModel(null);
    }

}
