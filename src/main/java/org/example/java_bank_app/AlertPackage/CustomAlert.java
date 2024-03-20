package org.example.java_bank_app.AlertPackage;

public class CustomAlert {
    public static void showInfoAlert(String text) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
