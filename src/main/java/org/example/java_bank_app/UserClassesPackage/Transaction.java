package org.example.java_bank_app.UserClassesPackage;

public class Transaction {
    private User sender;
    private Wallet sender_wallet;
    private Wallet reciver_wallet;
    private int transfer_amount;
    private String target_username;
    private String transaction_type;


    public Transaction(User sender, Wallet sender_wallet, Wallet reciver_wallet, int transfer_amount, String target_username, String transaction_type) {
        this.sender = sender;
        this.sender_wallet = sender_wallet;
        this.reciver_wallet = reciver_wallet;
        this.transfer_amount = transfer_amount;
        this.target_username = target_username;
        this.transaction_type =transaction_type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sender=" + sender +
                ", sender_wallet=" + sender_wallet +
                ", reciver_wallet=" + reciver_wallet +
                ", transfer_amount=" + transfer_amount +
                ", target_username='" + target_username + '\'' +
                ", transaction_type='" + transaction_type + '\'' +
                '}';
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Wallet getSender_wallet() {
        return sender_wallet;
    }

    public void setSender_wallet(Wallet sender_wallet) {
        this.sender_wallet = sender_wallet;
    }

    public Wallet getReciver_wallet() {
        return reciver_wallet;
    }

    public void setReciver_wallet(Wallet reciver_wallet) {
        this.reciver_wallet = reciver_wallet;
    }

    public int getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(int transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public String getTarget_username() {
        return target_username;
    }

    public void setTarget_username(String target_username) {
        this.target_username = target_username;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
}
