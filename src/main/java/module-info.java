module com.example.monopolyjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.monopolyjavafx to javafx.fxml;
    exports com.example.monopolyjavafx;
    exports GameBoardImplement;
    opens GameBoardImplement to javafx.fxml;
}