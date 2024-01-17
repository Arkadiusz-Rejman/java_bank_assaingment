package org.example.java_bank_app.UserClassesPackage;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;

import java.math.BigDecimal;
import java.util.ArrayList;

public class User {
    private final int id;
    private final String username, password;
    private ObservableList<Wallet> wallets;


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

    public void setWallets(ObservableList<Wallet> wallets) { this.wallets = wallets; }


    public void addWallet(CurrencyCode currencyCode, BigDecimal balance, String name ){
        wallets.add(new Wallet(currencyCode, balance, name));
    }


}
