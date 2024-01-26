package org.example.java_bank_app.UserClassesPackage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private final Wallet sender_wallet;
    private final Wallet reciver_wallet;
    private final String transaction_type;
    private final BigDecimal transfer_amount;
    //private final BigDecimal balanceBefore;
    //private final BigDecimal balanceAfter;
    //LocalDateTime transactionDateTime;


    public Transaction(Wallet sender_wallet, Wallet reciver_wallet, BigDecimal transfer_amount, String transaction_type) {
//        this.transactionDateTime = transactionDateTime;
//        this.balanceBefore = sender_wallet.getMoneyAmount();
//        this.balanceAfter = this.balanceBefore.subtract(transfer_amount);
        this.sender_wallet = sender_wallet;
        this.reciver_wallet = reciver_wallet;
        this.transfer_amount = transfer_amount;
        this.transaction_type =transaction_type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                ", sender_wallet=" + sender_wallet +
                ", reciver_wallet=" + reciver_wallet +
                ", transfer_amount=" + transfer_amount +
                ", transaction_type='" + transaction_type + '\'' +
                '}';
    }


    public Wallet getSender_wallet() {
        return sender_wallet;
    }
    public Wallet getReciver_wallet() {
        return reciver_wallet;
    }
    public BigDecimal getTransfer_amount() {
        return transfer_amount;
    }
    public String getTransaction_type() {
        return transaction_type;
    }
    //public BigDecimal getBalanceBefore() { return balanceBefore; }
    //public BigDecimal getBalanceAfter() { return balanceAfter; }
    //public LocalDateTime getTransactionDateTime() { return transactionDateTime; }
}
