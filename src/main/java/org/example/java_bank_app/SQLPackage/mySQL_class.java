package org.example.java_bank_app.SQLPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

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

    private static boolean isUsernameAvaliable(String username) {

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
                wallets.add(new Wallet(currencyCode, balance, name));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wallets;
    }

    public static void addWallet(User user, Wallet wallet){

        try {
            Connection connection = DriverManager.getConnection(DB_url,DB_username,DB_password);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO wallet(id_user,balance,currencyCode,name) " + "VALUES(?,?,?,?)"
            );

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setBigDecimal(2, wallet.getMoneyAmount());
            preparedStatement.setString(3, wallet.getCurrency().getCurrencyCode().toString());
            preparedStatement.setString(4, wallet.getName());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //class "}"
    }
