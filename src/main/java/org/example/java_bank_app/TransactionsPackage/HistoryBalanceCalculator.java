package org.example.java_bank_app.TransactionsPackage;

import org.example.java_bank_app.UserClassesPackage.Wallet;

import java.math.BigDecimal;
import java.util.HashMap;

public class HistoryBalanceCalculator {
    BigDecimal balanceBefore;
    BigDecimal balanceAfter;
    private final HashMap<Integer, BigDecimal> walletsBalance;

    public HistoryBalanceCalculator(){
        walletsBalance = new HashMap<>();
    }

    public void subtract(Wallet wallet, BigDecimal moneyAmount){
        if(!walletsBalance.containsKey(wallet.getId()))  {
            balanceBefore = wallet.getMoneyAmount().add(moneyAmount);
            balanceAfter = balanceBefore.subtract(moneyAmount);
            walletsBalance.put(wallet.getId(), balanceBefore);
        }else{
            balanceBefore = walletsBalance.get(wallet.getId()).add(moneyAmount);
            balanceAfter = balanceBefore.subtract(moneyAmount);
            walletsBalance.put(wallet.getId(), balanceBefore);
        }
    }

    public void add(Wallet wallet, BigDecimal moneyAmount){
        if(!walletsBalance.containsKey(wallet.getId()))  {
            balanceBefore = wallet.getMoneyAmount().subtract(moneyAmount);
            balanceAfter = balanceBefore.add(moneyAmount);
            walletsBalance.put(wallet.getId(), balanceBefore);
        }else{
            balanceBefore = walletsBalance.get(wallet.getId()).subtract(moneyAmount);
            balanceAfter = balanceBefore.add(moneyAmount);
            walletsBalance.put(wallet.getId(), balanceBefore);
        }
    }

    public BigDecimal getBalanceBefore(){
        return this.balanceBefore;
    }

    public BigDecimal getBalanceAfter(){
        return this.balanceAfter;
    }
}
