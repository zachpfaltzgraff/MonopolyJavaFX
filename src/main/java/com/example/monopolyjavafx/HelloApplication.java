package com.example.monopolyjavafx;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private GridPane gameGrid = new GridPane();
    GameBoard gameBoard = new GameBoard();
    @Override
    public void start(Stage stage) throws IOException {
        createGameGrid();

        gameGrid.setGridLinesVisible(true); // testing

        Scene scene = new Scene(gameGrid, 800, 800);
        stage.setTitle("Monopoly");
        Image icon = new Image("../icon.png");
        stage.getIcons().add(icon);
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
                Label label = new Label();
                label.setWrapText(true);
                label.setAlignment(Pos.CENTER);
                if (!gameBoard.getName(row, col).isEmpty()) {
                    label.setText(gameBoard.getName(row, col));
                }
                else {
                    label.setText("(" + row + "," + col + ")");
                }

                gameGrid.add(label, row, col);
                gameGrid.setHalignment(label, HPos.CENTER);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}