module org.example.appesports {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.appesports to javafx.fxml;
    exports org.example.appesports;
    exports org.example.appesports.Vista;
    opens org.example.appesports.Vista to javafx.fxml;
}