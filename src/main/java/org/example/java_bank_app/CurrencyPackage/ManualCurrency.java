package org.example.java_bank_app.CurrencyPackage;

import java.time.LocalDate;
import java.util.Date;

public class ManualCurrency extends Currency {
    private CurrencyCode currencyCode;
    private  double currencyRate;
    private LocalDate updateDate;


    public ManualCurrency (CurrencyCode currencyCode, double currencyRate, LocalDate updateDate){
        this.currencyCode = currencyCode;
        this.currencyRate = currencyRate;
        this.updateDate = updateDate;
    }

    //GETTERS
    @Override
    public CurrencyCode getCurrencyCode() {
        return this.currencyCode;
    }

    @Override
    public double getCurrencyRate() {
        return this.currencyRate;
    }

    @Override
    public LocalDate getUpdateDate() {
        return this.updateDate;
    }

    //SETTERS

    @Override
    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setCurrencyRate(double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }
}
