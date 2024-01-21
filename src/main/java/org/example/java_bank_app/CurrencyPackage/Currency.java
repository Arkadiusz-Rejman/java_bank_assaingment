package org.example.java_bank_app.CurrencyPackage;

import java.time.LocalDate;

public abstract class Currency {
    public abstract CurrencyCode getCurrencyCode();

    public abstract double getCurrencyRate();

    public abstract LocalDate getUpdateDate();

    public abstract void setCurrencyCode(CurrencyCode currencyCode);
}
