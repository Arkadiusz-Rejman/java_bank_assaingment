package org.example.java_bank_app.SQLPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;
import org.example.java_bank_app.TransactionsPackage.HistoryBalanceCalculator;
import org.example.java_bank_app.TransactionsPackage.HistoryTransaction;
import org.example.java_bank_app.TransactionsPackage.Transaction;
import org.example.java_bank_app.TransactionsPackage.TransactionType;
import org.example.java_bank_app.UserClassesPackage.Status;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class mySQL_class{
    private static final String DB_url = "jdbc:mysql://127.0.0.1:3306/bankapp";
    private static final String DB_username = "root";
    private static final String DB_password = "java1234";




    //User Operations

    public static User validateLogin(String username, String password) {
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_url,DB_username,DB_password);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE username = ? AND userpassword = ?"
            );

            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int userId = resultSet.getInt("id");
                return new User(userId,username,password);
            }

        }catch(SQLException e){
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    //true -> register succes
    public static boolean register(String username,String password){
        if(!isUsernameAvaliable(username)){
            try {
                Connection connection = DriverManager.getConnection(DB_url,DB_username,DB_password);

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO user(username,userpassword) " + "VALUES(?,?)"
                );

                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);

                preparedStatement.executeUpdate();

                return true;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public static boolean isUsernameAvaliable(String username) {
        try {
            Connection connection = DriverManager.getConnection(DB_url, DB_username, DB_password);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE username = ?"
            );
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    //Wallet Operations

    public static ObservableList<Wallet> getUserWallets(User user) {
        ObservableList<Wallet> wallets = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection(DB_url, DB_username, DB_password);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM wallet WHERE id_user = ?"
            );
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                BigDecimal balance = resultSet.getBigDecimal("balance");
                CurrencyCode currencyCode = CurrencyCode.valueOf(resultSet.getString("currencyCode"));
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                Status status = Status.valueOf(resultSet.getString("status"));
                wallets.add(new Wallet(id, user.getId(), currencyCode, balance, name, status));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wallets;
    }



    public static void addWallet(User user, BigDecimal balance, CurrencyCode currencyCode, String name){

        try {
            Connection connection = DriverManager.getConnection(DB_url,DB_username,DB_password);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO wallet(id_user,balance,currencyCode,name,status) " + "VALUES(?,?,?,?, 'ACTIVE')"
            );

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setBigDecimal(2, balance);
            preparedStatement.setString(3, currencyCode.toString());
            preparedStatement.setString(4, name);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteWallet(Wallet wallet){
        try{
            Connection connection = DriverManager.getConnection(DB_url,DB_username,DB_password);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE wallet SET status = 'DELETED' WHERE id = ?"
            );

            preparedStatement.setInt(1, wallet.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static int find_user_id_by_nickname(String target_username) {
        int id = 0;
        try {

            Connection connection = DriverManager.getConnection(DB_url, DB_username, DB_password);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE username = ?"
            );
            preparedStatement.setString(1, target_username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public static Wallet getWalletByID(int id){
        Wallet wallet = null;

        try {
            Connection connection = DriverManager.getConnection(DB_url, DB_username, DB_password);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM wallet WHERE id = ?"
            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int identifier = resultSet.getInt("id");
                int id_user = resultSet.getInt("id_user");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                CurrencyCode currencyCode = CurrencyCode.valueOf(resultSet.getString("currencyCode"));
                String name = resultSet.getString("name");
                Status status = Status.valueOf(resultSet.getString("status"));
                wallet = new Wallet(identifier, id_user, currencyCode, balance, name, status);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wallet;
    }

    public static ObservableList<Wallet> getUserWallets_byid(int user_id) {
        ObservableList<Wallet> wallets = FXCollections.observableArrayList();

        try {
            Connection connection = DriverManager.getConnection(DB_url, DB_username, DB_password);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM wallet WHERE id_user = ?"
            );
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BigDecimal balance = resultSet.getBigDecimal("balance");
                CurrencyCode currencyCode = CurrencyCode.valueOf(resultSet.getString("currencyCode"));
                String name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                Status status = Status.valueOf(resultSet.getString("status"));
                wallets.add(new Wallet(id, user_id, currencyCode, balance, name, status));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wallets;
    }

    public static void makeTransaction(Transaction transaction) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_url,DB_username,DB_password);

        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE wallet SET balance = balance + ? WHERE id_user = ? AND name = ?"
        );
        preparedStatement.setBigDecimal(1, transaction.getTransfer_amount());
        preparedStatement.setInt(2,transaction.getReciver_wallet().getId_user());
        preparedStatement.setString(3,transaction.getReciver_wallet().getName());

        preparedStatement.executeUpdate();

        PreparedStatement preparedStatement2 = connection.prepareStatement(
                "UPDATE wallet SET balance = balance - ? WHERE id_user = ? AND name = ?"
        );
        preparedStatement2.setBigDecimal(1, transaction.getTransfer_amount());
        preparedStatement2.setInt(2,transaction.getSender_wallet().getId_user());
        preparedStatement2.setString(3,transaction.getSender_wallet().getName());

        preparedStatement2.executeUpdate();

        PreparedStatement preparedStatement3 = connection.prepareStatement(
                "INSERT INTO transactions(transaction_amount, transaction_date, sender_wallet_id, receiver_wallet_id) " + "VALUES(?,CURRENT_TIMESTAMP,?,?)"
        );
        preparedStatement3.setBigDecimal(1,transaction.getTransfer_amount());
        preparedStatement3.setInt(2, transaction.getSender_wallet().getId());
        preparedStatement3.setInt(3, transaction.getReciver_wallet().getId());
        preparedStatement3.executeUpdate();

    }


    public static ObservableList<HistoryTransaction> getUserTransactions(User user) {
        ObservableList<HistoryTransaction> transactions = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(DB_url, DB_username, DB_password);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT transactions.*\n" +
                            "FROM transactions\n" +
                            "JOIN wallet AS sender_wallets ON transactions.sender_wallet_id = sender_wallets.id\n" +
                            "JOIN wallet AS receiver_wallets ON transactions.receiver_wallet_id = receiver_wallets.id\n" +
                            "JOIN user ON sender_wallets.id_user= user.id OR receiver_wallets.id_user = user.id\n" +
                            "WHERE user.id = ? ORDER BY transaction_date DESC;"
            );

            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            HistoryBalanceCalculator historyBalanceCalculator = new HistoryBalanceCalculator();

            while (resultSet.next()) {
                LocalDateTime transactionDate = resultSet.getTimestamp("transaction_date").toLocalDateTime();
                BigDecimal transactionAmount = resultSet.getBigDecimal("transaction_amount");
                int senderWalletID = resultSet.getInt("sender_wallet_id");
                int receiverWalletID = resultSet.getInt("receiver_wallet_id");

                TransactionType transactionType;
                Wallet whichWallet;
                Wallet senderWallet = getWalletByID(senderWalletID);
                Wallet receiverWallet = getWalletByID(receiverWalletID);

                if(user.getWallets().stream().anyMatch(wallet -> wallet.getId() == senderWalletID)) {
                    transactionType = TransactionType.SEND;
                    whichWallet = senderWallet;
                    historyBalanceCalculator.subtract(whichWallet, transactionAmount);
                }
                else {
                    transactionType = TransactionType.RECEIVE;
                    whichWallet = receiverWallet;
                    historyBalanceCalculator.add(whichWallet, transactionAmount);
                }

                HistoryTransaction historyTransaction = new HistoryTransaction(senderWallet, receiverWallet, transactionAmount, transactionType, transactionDate, whichWallet, historyBalanceCalculator.getBalanceBefore(), historyBalanceCalculator.getBalanceAfter());
                transactions.add(historyTransaction);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    public static void ChangeWalletName(int id,String newname) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_url, DB_username, DB_password);
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE wallet SET name = ? WHERE id = ?"
        );
        preparedStatement.setString(1, newname);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    //class "}"
    }
