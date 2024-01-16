module org.example.java_bank_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires com.google.gson;


    opens org.example.java_bank_app to javafx.fxml;
    exports org.example.java_bank_app;
    exports org.example.java_bank_app.CurrencyPackage;
    opens org.example.java_bank_app.CurrencyPackage to javafx.fxml;
    exports org.example.java_bank_app.SQLPackage;
    opens org.example.java_bank_app.SQLPackage to javafx.fxml;
    exports org.example.java_bank_app.ControllersPackage;
    opens org.example.java_bank_app.ControllersPackage to javafx.fxml;
    exports org.example.java_bank_app.UserClassesPackage;
    opens org.example.java_bank_app.UserClassesPackage to javafx.fxml;
}