package com.example.monopolyjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientApplication extends Application {
    private final GridPane clientGrid = new GridPane();
    PrintWriter output;
    private final int WIDTH = 300;
    private final int HEIGHT = 550;

    @Override
    public void start(Stage stage) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        output = new PrintWriter(socket.getOutputStream(), true);

        createControl();
        addButtons();

        Scene scene = new Scene(clientGrid, WIDTH, HEIGHT);
        stage.setTitle("Monopoly Player");
        stage.getIcons().add(new Image("file:sprites/icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    private void addButtons() {
        Button readyUp = new Button("Ready Up");
        readyUp.setMinSize(20, 30);
        readyUp.setOnAction(event -> output.println("hello")); // Send "hello" to server
        clientGrid.add(readyUp, 1, 0);
    }

    private void createControl() {
        for (int i = 0; i < 7; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight((double) HEIGHT / 7);
            clientGrid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < 3; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth((double) WIDTH / 3);
            clientGrid.getColumnConstraints().add(columnConstraints);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
