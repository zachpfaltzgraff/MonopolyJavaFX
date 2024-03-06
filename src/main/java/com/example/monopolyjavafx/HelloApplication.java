package com.example.monopolyjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private GridPane gameGrid = new GridPane();
    @Override
    public void start(Stage stage) throws IOException {
        createGameGrid();
        gameGrid.setGridLinesVisible(true);

        Scene scene = new Scene(gameGrid, 800, 800);
        stage.setTitle("Monopoly");
        stage.setScene(scene);
        stage.show();
    }

    public void createGameGrid() {
        for (int i = 0; i < 9; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            if (i == 0 || i == 8) {
                rowConstraints.setPercentHeight(15.0);
            } else {
                rowConstraints.setPercentHeight(70.0 / 7);
            }
            gameGrid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < 9; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            if (i == 0 || i == 8) {
                colConstraints.setPercentWidth(15.0);
            } else {
                colConstraints.setPercentWidth(70.0 / 7);
            }
            gameGrid.getColumnConstraints().add(colConstraints);
        }

        int numRows = 9;
        int numCols = 9;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                // Add your content to each cell of the grid
                // For simplicity, let's just add a label with row and column index
                gameGrid.add(new javafx.scene.control.Label("(" + row + ", " + col + ")"), col, row);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}