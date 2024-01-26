package org.example.java_bank_app.TransactionsPackage;

import org.example.java_bank_app.UserClassesPackage.Wallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistoryTrasaction extends Transaction{

    private final BigDecimal balanceBefore;
    private final BigDecimal balanceAfter;
    LocalDateTime transactionDateTime;

    //Tutaj musi byc trasaction type a nie u rodzica!
    public HistoryTrasaction(
            Wallet sender_wallet, Wallet reciver_wallet, BigDecimal transfer_amount, String transaction_type,
            LocalDateTime transactionDateTime, BigDecimal balance
    ) {
        super(sender_wallet, reciver_wallet, transfer_amount, transaction_type);
        this.transactionDateTime = transactionDateTime;
        this.balanceBefore = balance;
        this.balanceAfter = this.balanceBefore.subtract(transfer_amount);

    }

    public BigDecimal getBalanceBefore() { return balanceBefore; }

    public BigDecimal getBalanceAfter() { return balanceAfter; }

    public LocalDateTime getTransactionDateTime() { return transactionDateTime; }
}
