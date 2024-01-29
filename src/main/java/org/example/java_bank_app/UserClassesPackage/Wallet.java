package org.example.java_bank_app.UserClassesPackage;

import org.example.java_bank_app.CurrencyPackage.AutomaticCurrency;
import org.example.java_bank_app.CurrencyPackage.Currency;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;

import java.math.BigDecimal;

public class Wallet {
    int id, id_user;
    private final Currency currency;
    private BigDecimal moneyAmount;
    private String name;
    private Status status;

    public Wallet(int id, int id_user, CurrencyCode currencyCode, BigDecimal moneyAmount, String name, Status status){
        currency = new AutomaticCurrency(currencyCode);
        this.moneyAmount = moneyAmount;
        this.name = name;
        this.id = id;
        this.id_user = id_user;
        this.status = status;

    }
    //GETTERS
    public Currency getCurrency() {
        return this.currency;
    }
    public Status getStatus(){ return this.status; }
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

    @Override
    public String toString() {
        return getName()+"\t"+"["+getMoneyAmount()+getCurrency().getCurrencyCode()+"]";
    }
}
