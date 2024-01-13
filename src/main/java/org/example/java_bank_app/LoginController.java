package org.example.java_bank_app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

public class LoginController{
    @FXML
    public TextField usernameField;
    public PasswordField password_field;
    @FXML
    private Label welcomeText;
    @FXML
    private Button login_button;



    public void onLoginButtonClick(ActionEvent e){
        String username = usernameField.getText();
        String password = password_field.getText();

        User user = mySQL_class.validateLogin(username,password);

        if(user != null){
            System.out.println("udało sie jakims cudem XD");
        }

    }
}