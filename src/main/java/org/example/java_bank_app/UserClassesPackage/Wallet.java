package org.example.java_bank_app.UserClassesPackage;

import org.example.java_bank_app.CurrencyPackage.Currency;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;

import java.math.BigDecimal;

public class Wallet {
    private Currency currency;
    private BigDecimal moneyAmount;

    public Wallet(CurrencyCode currencyCode, BigDecimal moneyAmount){
        currency = new Currency(currencyCode);
        this.moneyAmount = moneyAmount;
    }

    //GETTERS
    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    //SETTERS
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
