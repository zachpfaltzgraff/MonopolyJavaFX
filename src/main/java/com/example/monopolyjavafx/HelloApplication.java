package com.example.monopolyjavafx;

import GameBoardImplement.GameBoard;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    private final GridPane gameGrid = new GridPane();
    private final GameBoard gameBoard = new GameBoard();
    private final int MAX_PLAYERS = 4;
    @Override
    public void start(Stage stage) throws IOException {
        setGraphics();
        setOutlines();
        createGameGrid();

        Scene scene = new Scene(gameGrid, 925, 800);
        stage.setTitle("Monopoly");
        stage.getIcons().add(new Image("file:sprites/icon.png"));
        stage.setScene(scene);
        stage.show();

        System.out.println("1");
        new Thread(() -> {
            try {
                System.out.println("2");
                connectClient();
                System.out.println("3");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void connectClient() throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);

        System.out.println("4");
        for (int i = 0; i < MAX_PLAYERS; i++) {
            System.out.println("5");
            int finalI = i;
            new Thread(() -> {
                System.out.println("6");
                try {
                    Socket playerSocket = serverSocket.accept();
                    System.out.println("Player # " + finalI + " connected");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
                    String data;
                    while((data = reader.readLine()) != null) {
                        System.out.println("Received from player " + finalI + ": " + data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
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

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
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

                gameGrid.add(label, row, col);
                label.setFont(new Font("Lato", 16));
                label.setTextAlignment(TextAlignment.CENTER);
                GridPane.setHalignment(label, HPos.CENTER);
                GridPane.setValignment(label, VPos.TOP);
            }
        }
    }

    private void setOutlines() {
        for (int i = 0; i < 8; i++) {
            addOutlines(i, 0);
            addOutlines(i, 8);
            addOutlines(0, i);
            addOutlines(8, i);
        }
    }

    private void addOutlines(int column, int row) {
        Pane cell = new Pane();

        BorderStroke borderStroke =
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1));
        Border border = new Border(borderStroke);
        cell.setBorder(border);

        gameGrid.add(cell, column, row);
    }

    private void setGraphics() {
        // start Square
        ImageView imageView = new ImageView("file:sprites/start.png");
        gameGrid.add(imageView, 0, 0);

        // Middle Square
        ImageView centerImage = new ImageView("file:sprites/middleLogo.png");
        centerImage.setFitHeight(100);
        centerImage.setFitWidth(374);
        gameGrid.add(centerImage, 4, 4);
        GridPane.setHalignment(centerImage, HPos.CENTER);

        addTopBottomCell(1, 0, "brown", "top");
        addTopBottomCell(3, 0, "brown", "top");
        addTopBottomCell(5, 0, "lightblue", "top");
        addTopBottomCell(7, 0, "lightblue", "top");

        addTopBottomCell(7, 8, "orange", "top");
        addTopBottomCell(5, 8, "orange", "top");
        addTopBottomCell(3, 8, "rgb(238, 120, 238)", "top");
        addTopBottomCell(1, 8, "rgb(238, 120, 238)", "top");

        addTopBottomCell(0, 1, "blue", "side");
        addTopBottomCell(0, 3, "blue", "side");
        addTopBottomCell(0, 5, "purple", "side");
        addTopBottomCell(0, 7, "purple", "side");

        addTopBottomCell(8, 5, "red", "side");
        addTopBottomCell(8, 7, "red", "side");
        addTopBottomCell(8, 1, "green", "side");
        addTopBottomCell(8, 3, "green", "side");

    }

    private void addTopBottomCell(int column, int row, String color, String topOrSide) {
        Pane cell = new Pane();
        if (topOrSide.equals("top")) {
            cell.setMaxHeight(50);
        }
        else if (topOrSide.equals("side")) {
            cell.setMaxHeight(25);
        }

        cell.setStyle("-fx-background-color: "  + color + ";");
        gameGrid.add(cell, column, row);
        GridPane.setValignment(cell, VPos.TOP);
    }

    public static void main(String[] args) {
        launch();
    }
}