package org.example.java_bank_app.ControllersPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.SQLPackage.*;

public class RegisterController {
    public Button register_button;
    @FXML
    private Label gotologin_label;

    @FXML
    private PasswordField r_password_field;

    @FXML
    private PasswordField r_retype_password_field;

    @FXML
    private TextField r_username_field;


    public void onRegisterButtonClick(ActionEvent e){
     String username = r_username_field.getText().trim();
     String password = r_password_field.getText().trim();
     String re_password = r_retype_password_field.getText().trim();

     if(validateUserInput(username,password,re_password)){
         if(mySQL_class.register(username,password)){
             CustomAlert.showInfoAlert("Account created sucesfully");

         }else{
             CustomAlert.showInfoAlert("Username taken");
         }
     }else{
         CustomAlert.showInfoAlert("Username have to be at least 4 characters, check your re-type password also");
     }

    }
    private boolean validateUserInput(String username,String password, String re_password){
        return username.length() >= 4 && !password.isEmpty() && re_password.equals(password);
    }


}
