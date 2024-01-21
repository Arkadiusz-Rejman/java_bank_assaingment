package org.example.java_bank_app.CurrencyPackage;

import java.time.LocalDate;
import java.util.Date;

public class AutomaticCurrency extends Currency {
    private CurrencyCode currencyCode;

    public AutomaticCurrency(CurrencyCode currencyCode){
        this.currencyCode = currencyCode;
    }

    //GETTERS
    @Override
    public CurrencyCode getCurrencyCode() { return this.currencyCode; }

    @Override
    public double getCurrencyRate() { return CurrencyRateAPI.currencyRate(this.currencyCode); }

    @Override
    public LocalDate getUpdateDate() { return LocalDate.now(); }

    //SETTERS
    @Override
    public void setCurrencyCode(CurrencyCode currencyCode) { this.currencyCode = currencyCode; }
}


