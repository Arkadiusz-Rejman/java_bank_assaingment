package org.example.java_bank_app;

import java.math.BigDecimal;
import java.sql.*;

public class mySQL_class{
    private static final String DB_url = "jdbc:mysql://127.0.0.1:3306/bankapp";
    private static final String DB_username = "root";
    private static final String DB_password = "java1234";





    public static User validateLogin(String username, String password){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_url,DB_username,DB_password);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user WHERE username = ? AND userpassword = ?"
            );

            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next()){
                int userId = resultSet.getInt("id");
                BigDecimal currentBalance = resultSet.getBigDecimal("balance");
                return new User(userId,username,password,currentBalance);
            }

        }catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

}
