package org.example.java_bank_app.ControllersPackage;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.java_bank_app.LoginGUI;
import org.example.java_bank_app.SQLPackage.mySQL_class;
import org.example.java_bank_app.UserClassesPackage.User;
import java.io.IOException;


public class LoginController{
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField password_field;
    @FXML
    public Button register;




    public void onLoginButtonClick(ActionEvent e) throws IOException{
        String username = usernameField.getText();
        String password = password_field.getText();

        User user = mySQL_class.validateLogin(username,password);


        if(user != null){

            FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("loggedUser-view.fxml"));
            Parent root = fxmlLoader.load();

            user.setWallets(mySQL_class.getUserWallets(user));
            LoggedUserController loggedUserController = fxmlLoader.getController();
            loggedUserController.passUser(user);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setX(600);

            Stage currentStage = (Stage)((Node) e.getSource()).getScene().getWindow();
            currentStage.close();

            stage.show();
        }else{
            System.out.println("Nie znaleziono uzytkownika");
        }

    }
    
    public void onRegisterButtonClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("register-view.fxml"));
        Parent root1 = fxmlLoader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setX(600);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();
    }




}