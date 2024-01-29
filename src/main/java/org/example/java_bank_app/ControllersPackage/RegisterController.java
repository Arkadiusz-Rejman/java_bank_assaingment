package org.example.java_bank_app.ControllersPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.AnimationsPackage.CustomAnimations;
import org.example.java_bank_app.SQLPackage.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    public Label register_label;
    @FXML
    public Group register_button_group;
    @FXML
    private PasswordField r_password_field;

    @FXML
    private PasswordField r_retype_password_field;

    @FXML
    private TextField r_username_field;
    


    public void onRegisterButtonClick(MouseEvent e){
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        register_label.getStyleClass().add("register-text");


        CustomAnimations.glowOnMouseEnter(Color.GOLD, register_button_group);
    }
}
