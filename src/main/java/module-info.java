module org.example.java_bank_app {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.java_bank_app to javafx.fxml;
    exports org.example.java_bank_app;
}