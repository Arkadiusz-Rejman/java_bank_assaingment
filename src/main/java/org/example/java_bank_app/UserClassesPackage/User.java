package org.example.java_bank_app.UserClassesPackage;


import org.example.java_bank_app.CurrencyPackage.CurrencyCode;

import java.math.BigDecimal;

public class User {
    private final int id;
    private final String username, password;
    private final Wallet wallet;


    public User(int id, String username, String password, BigDecimal currnetBalance, CurrencyCode currencyCode) {
        this.id = id;
        this.username = username;
        this.password = password;
        wallet = new Wallet(currencyCode, currnetBalance);
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
    public Wallet getWallet() { return this.wallet; }


}
