package org.example.java_bank_app;


import java.math.BigDecimal;

public class User {
    private final int id;
    private final String username, password;
    private final BigDecimal currnetBalance;


    public User(int id, String username, String password, BigDecimal currnetBalance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.currnetBalance = currnetBalance;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getCurrnetBalance() {
        return currnetBalance;
    }


}
