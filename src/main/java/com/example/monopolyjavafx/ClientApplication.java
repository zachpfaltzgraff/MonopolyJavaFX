package com.example.monopolyjavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientApplication extends Application {
    private final GridPane clientGrid = new GridPane();
    PrintWriter output;
    private final int WIDTH = 300;
    private final int HEIGHT = 550;
    private static boolean isStarted = false;

    @Override
    public void start(Stage stage) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        output = new PrintWriter(socket.getOutputStream(), true);

        createControl();
        addButtons();

        new Thread(() -> {
            try {
                listenServer(socket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Scene scene = new Scene(clientGrid, WIDTH, HEIGHT);
        stage.setTitle("Monopoly Player");
        stage.getIcons().add(new Image("file:sprites/icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    private void listenServer(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String data;
        while ((data = reader.readLine()) != null) {
            System.out.println(data);
            String finalData = data;
            Platform.runLater(() -> {
                if (isStarted) {
                    removeLabels(0, 6, 3, 1);
                }
                Label label = new Label(finalData);
                label.setFont(new Font("Lato", 16));
                label.setAlignment(Pos.CENTER);
                clientGrid.add(label, 0, 6, 3, 1);

                if (finalData.equals("start")) {
                    addActions();
                    isStarted = true;
                    Label updatesLabel = new Label("Game Updates: ");
                    updatesLabel.setFont(new Font("Lato", 16));
                    clientGrid.add(updatesLabel, 0, 5, 3, 1);
                    GridPane.setHalignment(updatesLabel, HPos.LEFT);
                    GridPane.setValignment(updatesLabel, VPos.BOTTOM);
                }
            });
        }
    }

    private void removeLabels(int colIndex, int rowIndex, int colSpan, int rowSpan) {
        ObservableList<Node> children = clientGrid.getChildren();
        children.removeIf(node ->
                GridPane.getColumnIndex(node) >= colIndex &&
                        GridPane.getColumnIndex(node) < colIndex + colSpan &&
                        GridPane.getRowIndex(node) >= rowIndex &&
                        GridPane.getRowIndex(node) < rowIndex + rowSpan
        );
    }

    private void addActions() {
        Button rollDice = new Button("Roll Dice");
        rollDice.setFont(new Font("Lato", 16));
        clientGrid.add(rollDice, 0, 1);
        GridPane.setHalignment(rollDice, HPos.LEFT);
        GridPane.setValignment(rollDice, VPos.TOP);

        rollDice.setOnAction(e -> {
            output.println("roll");
        });
    }

    private void addButtons() {
        Button readyUp = new Button("Ready Up");
        readyUp.setMinSize(20, 30);
        readyUp.setOnAction(event -> {
            output.println("ready");
            readyUp.setDisable(true);
        });
        clientGrid.add(readyUp, 1, 0);
        GridPane.setHalignment(readyUp, HPos.CENTER);
        GridPane.setValignment(readyUp, VPos.TOP);
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
