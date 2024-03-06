package com.example.monopolyjavafx;
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
public class GameBoard extends SquarePiece {
    private SquarePiece[][] squarePieces = new SquarePiece[9][9];

    GameBoard() {
        initializeNames();
    }

    void initializeNames() {
        squarePieces[0][0].setName("Start");
    }
}
