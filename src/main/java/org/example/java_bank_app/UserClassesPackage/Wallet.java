package org.example.java_bank_app.UserClassesPackage;

import org.example.java_bank_app.CurrencyPackage.Currency;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;

import java.math.BigDecimal;

public class Wallet {
    int id, id_user;
    private Currency currency;
    private BigDecimal moneyAmount;
    private String name;

    public Wallet(int id, int id_user, CurrencyCode currencyCode, BigDecimal moneyAmount, String name){
        currency = new Currency(currencyCode);
        this.moneyAmount = moneyAmount;
        this.name = name;
        this.id = id;
        this.id_user = id_user;

    }

    //GETTERS
    public Currency getCurrency() {
        return this.currency;
    }
    public BigDecimal getMoneyAmount() {
        return this.moneyAmount;
    }
    public String getName() { return this.name; }
    public int getId() { return this.id; }
    public int getId_user() { return this.id_user; }


    //SETTERS
    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
