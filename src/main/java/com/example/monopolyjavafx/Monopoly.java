package com.example.monopolyjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Monopoly extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GridPane gameGrid = new GridPane();
        Scene scene = new Scene(gameGrid, 1080, 720);
        stage.setTitle("Monopoly");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}