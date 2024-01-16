package org.example.java_bank_app.SQLPackage;

import org.example.java_bank_app.CurrencyPackage.CurrencyCode;
import org.example.java_bank_app.UserClassesPackage.User;

import java.math.BigDecimal;
import java.sql.*;

public class mySQL_class{
    private static final String DB_url = "jdbc:mysql://127.0.0.1:3306/bankapp";
    private static final String DB_username = "root";
    private static final String DB_password = "java1234";





    public static User validateLogin(String username, String password) throws SQLException {
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
                BigDecimal currentBalance = resultSet.getBigDecimal("balance");
                CurrencyCode currencyCode = CurrencyCode.valueOf(resultSet.getString("currencyCode"));

                return new User(userId,username,password,currentBalance, currencyCode);
            }

        }catch(SQLException e){
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        finally {
            if(connection != null) connection.close();
        }

        return null;
    }

}
