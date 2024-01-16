package org.example.java_bank_app.ControllersPackage;

import com.mysql.cj.log.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.java_bank_app.LoginGUI;
import org.example.java_bank_app.SQLPackage.mySQL_class;
import org.example.java_bank_app.UserClassesPackage.User;


import java.io.IOException;
import java.sql.SQLException;

public class LoginController{
    private Parent root;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField password_field;
    @FXML
    public Button register;
    @FXML
    private Label welcomeText;
    @FXML
    private Button login_button;



    public void onLoginButtonClick(ActionEvent e){
        String username = usernameField.getText();
        String password = password_field.getText();

        User user = null;
        try {
            user = mySQL_class.validateLogin(username,password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        if(user != null){
            System.out.println("ID Usera:" + user.getId());
            System.out.println("Nazwa Usera: " + user.getUsername());
            System.out.println("Pieniadze na koncie: " + user.getWallet().getMoneyAmount() + " " + user.getWallet().getCurrency().getCurrencyCode().toString());
            System.out.println("Kurs waluty do PLN: " + user.getWallet().getCurrency().getExchangeRate());
        }else{
            System.out.println("Nie znaleziono uzytkownika");
        }

    }
    
    public void onRegisterButtonClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("register-view.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setX(600);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();


    }


}