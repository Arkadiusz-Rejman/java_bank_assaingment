package org.example.java_bank_app.TransactionsPackage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.example.java_bank_app.UserClassesPackage.Wallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistoryTransaction extends Transaction{

    private final BigDecimal balanceBefore;
    private final BigDecimal balanceAfter;
    private final TransactionType transactionType;
    private final LocalDateTime transactionDateTime;
    private final Wallet wallet;


    public HistoryTransaction(
            Wallet sender_wallet, Wallet reciver_wallet, BigDecimal transfer_amount, TransactionType transaction_type,
            LocalDateTime transactionDateTime, Wallet wallet, BigDecimal balanceBefore, BigDecimal balanceAfter
    ) {
        super(sender_wallet, reciver_wallet, transfer_amount);
        this.wallet = wallet;
        this.transactionType = transaction_type;
        this.transactionDateTime = transactionDateTime;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
    }


    //GETTERS
    public BigDecimal getBalanceBefore() { return balanceBefore; }

    public BigDecimal getBalanceAfter() { return balanceAfter; }

    public TransactionType getTransactionType() { return transactionType; }

    public LocalDateTime getTransactionDateTime() { return transactionDateTime; }
    public Wallet getWallet() { return wallet; }








    //PROPERTIES
    public ObjectProperty<LocalDateTime> getTransactionDateTimeProperty(){ return new SimpleObjectProperty<>(this.transactionDateTime); }
    public ObjectProperty<TransactionType> getTransactionTypeProperty() { return new SimpleObjectProperty<>(this.transactionType); }
    public ObjectProperty<BigDecimal> getBalanceBeforeProperty() { return new SimpleObjectProperty<>(this.balanceBefore); }
    public ObjectProperty<BigDecimal> getBalanceAfterProperty() { return new SimpleObjectProperty<>(this.balanceAfter); }
    public ObjectProperty<BigDecimal> getTransactionAmountProperty() { return new SimpleObjectProperty<>(super.getTransfer_amount()); }
    public StringProperty getSenderProperty() { return new SimpleStringProperty(super.getSender_wallet().getName()); }
    public StringProperty getReceiverProperty() { return new SimpleStringProperty(super.getReciver_wallet().getName()); }
    public StringProperty getWalletProperty() { return new SimpleStringProperty(this.wallet.getName()); }

    @Override
    public String toString() {
        return "Transaction{" +
                ", sender_wallet=" + super.getSender_wallet() +
                ", reciver_wallet=" + super.getReciver_wallet() +
                ", transfer_amount=" + super.getTransfer_amount() +
                ", transaction_type=" + this.transactionType +
                ", transactionDateTime=" + this.transactionDateTime +
                ", balanceBefore=" + this.balanceBefore +
                ", balanceAfter" + this.balanceAfter +
                ", onWallet= " + this.wallet +
                "} \n";
    }


}
