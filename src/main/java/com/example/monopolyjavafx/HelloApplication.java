package com.example.monopolyjavafx;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private final GridPane gameGrid = new GridPane();
    GameBoard gameBoard = new GameBoard();
    @Override
    public void start(Stage stage) throws IOException {
        createGameGrid();
        setGraphics();

        gameGrid.setGridLinesVisible(true); // testing

        Scene scene = new Scene(gameGrid, 925, 800);
        stage.setTitle("Monopoly");
        stage.getIcons().add(new Image("file:sprites/icon.png"));
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

                    if (gameBoard.getPrice(row,col) > 0) {
                        Label priceLabel = new Label("$" + gameBoard.getPrice(row, col));
                        gameGrid.add(priceLabel, row, col);

                        priceLabel.setFont(new Font("Lato", 16));
                        priceLabel.setTextAlignment(TextAlignment.CENTER);
                        GridPane.setHalignment(priceLabel, HPos.CENTER);
                        GridPane.setValignment(priceLabel, VPos.BOTTOM);
                    }
                }
                else {
                    label.setText("(" + row + "," + col + ")");
                }

                gameGrid.add(label, row, col);
                label.setFont(new Font("Lato", 16));
                label.setTextAlignment(TextAlignment.CENTER);
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.TOP);
            }
        }
    }

    public void setGraphics() {
        // start Square
        ImageView imageView = new ImageView("file:sprites/start.png");
        gameGrid.add(imageView, 0, 0);

    }

    public static void main(String[] args) {
        launch();
    }
}