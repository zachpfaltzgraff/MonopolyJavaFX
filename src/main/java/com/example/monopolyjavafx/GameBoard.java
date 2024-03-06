package com.example.monopolyjavafx;

public class GameBoard {
    private String[][] GameGrid = new String[11][11];

    /**
     * gameLogic
     * 0 = Property Squares
     * 9 = Property Square owned
     * 1 = RailRoad Squares
     * 2 = Utility / Tax Squares
     * 3 = Community Chest
     * 4 = Chance Square
     * 5 = Go To Jail
     * 6 = Jail / Just Visiting
     * 7 = Free Parking
     * 8 = Go / Start
     */
    private int[][] gameLogic = new int[11][11];

    GameBoard() {
        GameGrid[0][0] = "Start";
    }
}
