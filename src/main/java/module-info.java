module com.example.aspibot {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.aspibot to javafx.fxml;
    exports com.example.aspibot;
}