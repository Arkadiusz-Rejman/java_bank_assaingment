package org.example.java_bank_app.CurrencyPackage;

public class Currency {
    private CurrencyCode currencyCode; //Trzyznakowy kod waluty: PLN, EUR, itd... (w ENUMIE CurrencyCode)

    public Currency (CurrencyCode currencyCode){
        this.currencyCode = currencyCode;
    }

    //GETTERS
    public double getExchangeRate() {
        return CurrencyRateAPI.currencyRate(this.currencyCode);
    }

    public double getExchangeRate(CurrencyCode currencyCode) {
        return CurrencyRateAPI.currencyRate(currencyCode);
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    //SETTERS

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }
}
