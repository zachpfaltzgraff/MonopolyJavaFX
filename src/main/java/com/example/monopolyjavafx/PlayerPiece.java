package com.example.monopolyjavafx;

import javafx.scene.image.Image;

public class PlayerPiece {
    private int playerNumber;
    private int curRow;
    private int curCol;
    private int balance;

    PlayerPiece(int i) {
        this.playerNumber = i;
        this.curRow = 0;
        this.curCol = 0;
        this.balance = 1500;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getCurRow() {
        return curRow;
    }

    public void setCurRow(int curRow) {
        this.curRow = curRow;
    }

    public int getCurCol() {
        return curCol;
    }

    public void setCurCol(int curCol) {
        this.curCol = curCol;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
