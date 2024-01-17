package org.example.java_bank_app.UserClassesPackage;

import org.example.java_bank_app.CurrencyPackage.Currency;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;

import java.math.BigDecimal;

public class Wallet {
    private Currency currency;
    private BigDecimal moneyAmount;
    private String name;

    public Wallet(CurrencyCode currencyCode, BigDecimal moneyAmount, String name){
        currency = new Currency(currencyCode);
        this.moneyAmount = moneyAmount;
        this.name = name;
    }

    //GETTERS
    public Currency getCurrency() {
        return this.currency;
    }

    public BigDecimal getMoneyAmount() {
        return this.moneyAmount;
    }
    public String getName() { return this.name; }

    //SETTERS
    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
