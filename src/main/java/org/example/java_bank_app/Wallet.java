package org.example.java_bank_app;

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
