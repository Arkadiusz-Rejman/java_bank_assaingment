package org.example.java_bank_app;

public class Currency {
    private CurrencyCode currencyCode; //Trzyznakowy kod waluty: PLN, EUR, itd... (w ENUMIE CurrencyCode)
    private double exchangeRate; //Kurs waluty w stosunku do PLN

    public Currency (CurrencyCode currencyCode){
        this.currencyCode = currencyCode;
    }

    //GETTERS
    public double getExchangeRate() {
        return exchangeRate;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    //SETTERS

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }
}
