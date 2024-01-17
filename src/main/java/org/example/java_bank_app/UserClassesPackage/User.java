package org.example.java_bank_app.UserClassesPackage;


import org.example.java_bank_app.CurrencyPackage.CurrencyCode;

import java.math.BigDecimal;
import java.util.ArrayList;

public class User {
    private final int id;
    private final String username, password;
    private final ArrayList<Wallet> wallets;


    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.wallets = new ArrayList<>();
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

    public ArrayList<Wallet> getWallets() { return this.wallets; }


    public void addWallet(CurrencyCode currencyCode, BigDecimal balance ){
        wallets.add(new Wallet(currencyCode, balance));
    }


}
