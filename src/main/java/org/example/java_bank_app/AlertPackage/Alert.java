package org.example.java_bank_app.AlertPackage;

public class Alert {
    public static void showInfoAlert(String text) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
