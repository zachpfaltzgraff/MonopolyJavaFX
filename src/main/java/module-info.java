module com.example.monopolyjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.monopolyjavafx to javafx.fxml;
    exports com.example.monopolyjavafx;
}