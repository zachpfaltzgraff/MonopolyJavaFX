package com.example.monopolyjavafx;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    private final GridPane gameGrid = new GridPane();
    GameBoard gameBoard = new GameBoard();
    @Override
    public void start(Stage stage) throws IOException {
        setGraphics();
        createGameGrid();

        //gameGrid.setGridLinesVisible(true); // testing

        Scene scene = new Scene(gameGrid, 925, 800);
        stage.setTitle("Monopoly");
        stage.getIcons().add(new Image("file:sprites/icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    private void createGameGrid() {

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

    private void setGraphics() {
        // start Square
        ImageView imageView = new ImageView("file:sprites/start.png");
        gameGrid.add(imageView, 0, 0);

        // Middle Square
        ImageView centerImage = new ImageView("file:sprites/icon.png");
        gameGrid.add(centerImage, 4, 4);
        GridPane.setHalignment(centerImage, HPos.CENTER);

        addTopBottomCell(1, 0, "brown");
        addTopBottomCell(3, 0, "brown");
        addTopBottomCell(5, 0, "lblue");
        addTopBottomCell(7, 0, "lblue");

        addTopBottomCell(7, 8, "orange");
        addTopBottomCell(5, 8, "orange");
        addTopBottomCell(3, 8, "purple");
        addTopBottomCell(1, 8, "purple");
    }

    private void addTopBottomCell(int column, int row, String color) {
        Pane cell = new Pane();
        cell.setMaxHeight(50);

        if (color.equals("brown")) {
            cell.setStyle("-fx-background-color: brown;");
        }
        else if (color.equals("lblue")) {
            cell.setStyle("-fx-background-color: lightblue;");
        }
        else if (color.equals("orange")) {
            cell.setStyle("-fx-background-color: orange;");
        }
        else if (color.equals("purple")) {
            cell.setStyle("-fx-background-color: rgb(238, 120, 238);");
        }
        gameGrid.add(cell, column, row);
        GridPane.setValignment(cell, VPos.TOP);
    }


    public static void main(String[] args) {
        launch();
    }
}