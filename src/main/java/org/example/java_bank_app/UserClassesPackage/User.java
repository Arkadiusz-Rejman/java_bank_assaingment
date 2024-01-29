package org.example.java_bank_app.UserClassesPackage;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.java_bank_app.TransactionsPackage.HistoryTransaction;

import java.util.List;

public class User {
    private final int id;
    private final String username, password;
    private ObservableList<Wallet> wallets;
    private ObservableList<HistoryTransaction> transactions;


    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.wallets = FXCollections.observableArrayList();
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public ObservableList<Wallet> getWallets() { return this.wallets; }

    public void setWallets(ObservableList<Wallet> wallets) {
        List<Wallet> userWallets = wallets.stream().filter(wallet -> wallet.getStatus() != Status.DELETED).toList();
        this.wallets.setAll(userWallets)
        ;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
