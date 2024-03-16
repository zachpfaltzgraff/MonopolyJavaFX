package com.example.monopolyjavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloApplication extends Application {
    private final GridPane gameGrid = new GridPane();
    private GridPane[][] squareGrid = new GridPane[9][9];
    private final GameBoard gameBoard = new GameBoard();
    private Rectangle[] box = new Rectangle[4];
    private final int MAX_PLAYERS = 4;
    private final ExecutorService executorService = Executors.newFixedThreadPool(MAX_PLAYERS);
    Button startButton;
    private final List<Socket> clientSockets = new ArrayList<>();
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

        connectClients();
    }

    private void connectClients() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            for (int i = 0; i < MAX_PLAYERS; i++) {
                int finalI = i;
                executorService.submit(() -> { // add in player
                    try {
                        Socket playerSocket = serverSocket.accept();
                        System.out.println("Player connected");

                        // Add the connected socket to the list
                        clientSockets.add(playerSocket);

                        PlayerPiece player = new PlayerPiece(finalI);

                        startButton.setOnAction(e -> {
                            startButton.setVisible(false);
                            try {
                                serverSendToAll("start");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

                        clientListener(player, playerSocket, finalI);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clientListener(PlayerPiece player, Socket playerSocket, int i) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
        String data;
        while ((data = reader.readLine()) != null) {
            System.out.println(data);
            if (data.equals("ready")) {
                addPlayer(i, player);
            }
            else if (data.equals("roll")) {
                Random random = new Random();
                int roll1 = random.nextInt(4) + 1;
                int roll2 = random.nextInt(4) + 1;                int roll = roll1 + roll2;

                rollDice(roll, i, player);
            }
        }
    }

    private void rollDice(int roll, int playerIndex, PlayerPiece player) throws IOException {
        playerIndex++;
        serverSendToAll("Player " + playerIndex + " rolls a " + roll);
        int curRow = player.getCurRow();
        int curCol = player.getCurCol();

        for (int i = 0; i < roll; i++) {
            if (curRow < 8 && curCol == 0 ) {
                curRow++;
            } else if (curRow == 8 && curCol < 8) {
                curCol++;
            } else if (curRow > 0 && curCol == 8) {
                curRow--;
            } else if (curRow == 0 && curCol > 0) {
                curCol--;
            }
        }

        int finalCurCol = curCol;
        int finalCurRow = curRow;
        Platform.runLater(() -> {
            squareGrid[player.getCurRow()][player.getCurCol()].getChildren().remove(box[player.getPlayerNumber()]);

            player.setCurCol(finalCurCol);
            player.setCurRow(finalCurRow);

            if (player.getPlayerNumber() == 0) {
                squareGrid[player.getCurRow()][player.getCurCol()].add(box[player.getPlayerNumber()], 0, 0);
            } else if (player.getPlayerNumber() == 1) {
                squareGrid[player.getCurRow()][player.getCurCol()].add(box[player.getPlayerNumber()], 1, 0);
            } else if (player.getPlayerNumber() == 2) {
                squareGrid[player.getCurRow()][player.getCurCol()].add(box[player.getPlayerNumber()], 0, 1);
            } else if (player.getPlayerNumber() == 3) {
                squareGrid[player.getCurRow()][player.getCurCol()].add(box[player.getPlayerNumber()], 1, 1);
            }
        });
    }

    private void serverSendToAll(String message) throws IOException {
        for (Socket socket : clientSockets) {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            output.println(message);
        }
    }
    private void addPlayer(int i, PlayerPiece player) {
        Platform.runLater(() -> {
            Label label = new Label("Player " + (i + 1));
            label.setFont(new Font("Lato", 16));
            gameGrid.add(label, i + 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
            GridPane.setValignment(label, VPos.TOP);

            Label moneyLabel = new Label(String.valueOf("$" + player.getBalance()));
            moneyLabel.setFont(new Font("Lato", 16));
            gameGrid.add(moneyLabel, i + 1, 1);
            GridPane.setHalignment(moneyLabel, HPos.CENTER);
            GridPane.setValignment(moneyLabel, VPos.CENTER);

            box[i] = new Rectangle(15, 15);
            if (i == 0) {
                box[i].setFill(Color.RED);
            } else if (i == 1) {
                box[i].setFill(Color.BLUE);
            } else if (i == 2) {
                box[i].setFill(Color.BLACK);
            } else if (i == 3) {
                box[i].setFill(Color.CORAL);
            }

            if (i == 0) {
                squareGrid[0][0].add(box[i], 0, 0);
                GridPane.setValignment(box[i], VPos.BOTTOM);
                GridPane.setHalignment(box[i], HPos.RIGHT);
            } else if (i == 1) {
                squareGrid[0][0].add(box[i], 1, 0);
                GridPane.setValignment(box[i], VPos.BOTTOM);
                GridPane.setHalignment(box[i], HPos.LEFT);
            }else if (i == 2) {
                squareGrid[0][0].add(box[i], 0, 1);
                GridPane.setValignment(box[i], VPos.TOP);
                GridPane.setHalignment(box[i], HPos.RIGHT);
            }else if (i == 3) {
                squareGrid[0][0].add(box[i], 1, 1);
                GridPane.setValignment(box[i], VPos.TOP);
                GridPane.setHalignment(box[i], HPos.LEFT);
            }

        });
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

                squareGrid[row][col] = new GridPane();
                for (int i = 0; i < 2; i++) {
                    RowConstraints rowConstraints = new RowConstraints();
                    rowConstraints.setPercentHeight(100.0 / 2);
                    squareGrid[row][col].getRowConstraints().add(rowConstraints);
                }
                for (int i = 0; i < 2; i++) {
                    ColumnConstraints columnConstraints = new ColumnConstraints();
                    columnConstraints.setPercentWidth(100.0 / 2);
                    squareGrid[row][col].getColumnConstraints().add(columnConstraints);
                }
                gameGrid.add(squareGrid[row][col], row, col);
            }
        }
        startButton = new Button("Start Game");
        gameGrid.add(startButton, 4, 5);
        GridPane.setHalignment(startButton, HPos.CENTER);
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