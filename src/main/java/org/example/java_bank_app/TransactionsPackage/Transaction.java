package org.example.java_bank_app.TransactionsPackage;

import org.example.java_bank_app.UserClassesPackage.Wallet;
import java.math.BigDecimal;

public class Transaction {
    private final Wallet sender_wallet;
    private final Wallet reciver_wallet;
    private final BigDecimal transfer_amount;



    public Transaction(Wallet sender_wallet, Wallet reciver_wallet, BigDecimal transfer_amount) {
        this.sender_wallet = sender_wallet;
        this.reciver_wallet = reciver_wallet;
        this.transfer_amount = transfer_amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                ", sender_wallet=" + sender_wallet +
                ", reciver_wallet=" + reciver_wallet +
                ", transfer_amount=" + transfer_amount +
                "} \n";
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

}
