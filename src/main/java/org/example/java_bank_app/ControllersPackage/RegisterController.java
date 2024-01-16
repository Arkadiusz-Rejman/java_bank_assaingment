package org.example.java_bank_app.ControllersPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
     String username = r_username_field.getText();
     String password = r_password_field.getText();
     String re_password = r_retype_password_field.getText();

     if(validateUserInput(username,password,re_password)){
         if(mySQL_class.register(username,password)){
             showAlert_succes();
         }else{
             showAlert_taken();
         }
     }else{
         showAlert_fail();
     }

    }
    private boolean validateUserInput(String username,String password, String re_password){
        if(username.isEmpty() || password.isEmpty() || re_password.isEmpty()) return false;
        if(username.length() < 4) return false;
        if(!re_password.equals(password)) return false;
        return true;
    }

    private void showAlert_succes() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Account created sucesfully");

        alert.showAndWait();
    }

    private void showAlert_fail() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Username have to be at least 4 characters, check your re-type password also");

        alert.showAndWait();
    }
    private void showAlert_taken() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText("Username taken");

        alert.showAndWait();
    }
}
