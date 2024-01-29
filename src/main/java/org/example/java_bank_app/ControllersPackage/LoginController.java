package org.example.java_bank_app.ControllersPackage;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.AnimationsPackage.CustomAnimations;
import org.example.java_bank_app.LoginGUI;
import org.example.java_bank_app.SQLPackage.mySQL_class;
import org.example.java_bank_app.UserClassesPackage.User;
import org.w3c.dom.events.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField password_field;
    @FXML
    public Button register;

    @FXML
    ImageView RefreshImage, LoginImage, RegisterImage;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomAnimations.glowOnMouseEnter(
                Color.GOLD,
                RefreshImage, LoginImage, RegisterImage
        );
        CustomAnimations.scaleOnMousePress(
                RefreshImage, LoginImage, RegisterImage
        );

        password_field.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                // Wywołanie funkcji po naciśnięciu Enter
                try {
                    onLoginButtonClick();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @FXML
    public void onLoginButtonClick() throws IOException{
        String username = usernameField.getText();
        String password = password_field.getText();

        User user = mySQL_class.validateLogin(username,password);


        if(user != null){

            FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("loggedUser-view.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setX(600);

            user.setWallets(mySQL_class.getUserWallets(user));
            LoggedUserController loggedUserController = fxmlLoader.getController();
            loggedUserController.passUser(user);
            loggedUserController.passLoginStage(this.stage);
            loggedUserController.passStage(stage);

            this.stage.close();

            stage.show();
        }else{
            CustomAlert.showInfoAlert("User not found");
        }

    }

    @FXML
    public void onRegisterButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginGUI.class.getResource("register-view.fxml"));
        File cssFile = new File("src/main/resources/org/example/java_bank_app/register_style.css");
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());
        stage.setScene(scene);

        stage.setX(600);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();
    }

    @FXML
    public void refresh(){
        usernameField.clear();
        password_field.clear();
    }

    public void passStage(Stage stage){
        this.stage = stage;
    }

}