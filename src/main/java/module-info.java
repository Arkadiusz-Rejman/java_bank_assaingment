module org.example.java_bank_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires com.google.gson;


    opens org.example.java_bank_app to javafx.fxml;
    exports org.example.java_bank_app;
}