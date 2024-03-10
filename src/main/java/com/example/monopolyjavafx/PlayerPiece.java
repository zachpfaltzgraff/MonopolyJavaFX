package com.example.monopolyjavafx;

public class PlayerPiece {
    private int curRow;
    private int curCol;
    private int balance;

    PlayerPiece() {
        this.curRow = 0;
        this.curCol = 0;
        this.balance = 1500;
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
