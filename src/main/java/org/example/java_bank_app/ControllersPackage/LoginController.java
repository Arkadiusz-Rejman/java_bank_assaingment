package org.example.java_bank_app.ControllersPackage;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


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




    public void onLoginButtonClick(ActionEvent e){
        String username = usernameField.getText();
        String password = password_field.getText();

        User user = mySQL_class.validateLogin(username,password);


        if(user != null){
            System.out.println("ID Usera:" + user.getId());
            System.out.println("Nazwa Usera: " + user.getUsername());
            System.out.println("Portfele: " + user.getWallets());
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