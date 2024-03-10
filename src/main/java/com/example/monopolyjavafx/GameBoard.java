package GameBoardImplement;
/**
 * gameLogic
 * 0 = Property Squares
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

    public GameBoard() {
        initializePieces();
    }

    private void initializePieces() {
        squarePieces[0][0] =
                new SquarePiece("Start", 8, 0);
        squarePieces[1][0] =
                new SquarePiece("Mediterranean Ave.", 0, 60);
        squarePieces[2][0] =
                new SquarePiece("Community Chest", 3, 0);
        squarePieces[3][0] =
                new SquarePiece("Baltic Ave.", 0, 60);
        squarePieces[4][0] =
                new SquarePiece("Reading Railroad", 1, 200);
        squarePieces[5][0] =
                new SquarePiece("Oriental Ave.", 0, 100);
        squarePieces[6][0] =
                new SquarePiece("Chance", 4, 0);
        squarePieces[7][0] =
                new SquarePiece("Vermont Ave.", 0, 110);
        squarePieces[8][0] =
                new SquarePiece("Jail", 6, 0);

        squarePieces[1][8] =
                new SquarePiece("St. Charles Place", 0, 140);
        squarePieces[2][8] =
                new SquarePiece("Electric Company", 2, 150);
        squarePieces[3][8] =
                new SquarePiece("States Avenue", 0, 150);
        squarePieces[4][8] =
                new SquarePiece("Pennsylvania Railroad", 1, 200);
        squarePieces[5][8] =
                new SquarePiece("St. James Place", 0, 180);
        squarePieces[6][8] =
                new SquarePiece("Community Chest", 3, 0);
        squarePieces[7][8] =
                new SquarePiece("Tennessee Ave.", 0, 200);
        squarePieces[8][8] =
                new SquarePiece("Free Parking", 7, 0);

        squarePieces[8][7] =
                new SquarePiece("kentucky Ave.", 0, 220);
        squarePieces[8][6] =
                new SquarePiece("Chance", 4, 0);
        squarePieces[8][5] =
                new SquarePiece("Indiana Ave.", 0, 240);
        squarePieces[8][4] =
                new SquarePiece("B&O RailRoad", 1, 200);
        squarePieces[8][3] =
                new SquarePiece("Atlantic Ave.", 0, 260);
        squarePieces[8][2] =
                new SquarePiece("Water Works", 2, 150);
        squarePieces[8][1] =
                new SquarePiece("Tennessee Ave.", 0, 260);
        squarePieces[0][8] =
                new SquarePiece("Go to Jail", 5, 0);

        squarePieces[0][7] =
                new SquarePiece("Pacific Ave.", 0, 300);
        squarePieces[0][6] =
                new SquarePiece("Community Chest", 3, 0);
        squarePieces[0][5] =
                new SquarePiece("NC Ave.", 0, 320);
        squarePieces[0][4] =
                new SquarePiece("Short Line", 1, 200);
        squarePieces[0][3] =
                new SquarePiece("park Place", 0, 350);
        squarePieces[0][2] =
                new SquarePiece("Luxury Tax", 2, 100);
        squarePieces[0][1] =
                new SquarePiece("Boardwalk", 0, 400);

        for (int i = 0; i < squarePieces.length; i++) {
            for (int j = 0; j < squarePieces[i].length; j++) {
                if (squarePieces[i][j] == null) {
                    squarePieces[i][j] = new SquarePiece(); // Or whichever constructor you want to use
                }
            }
        }
    }

    public String getName(int row, int col) {
        return squarePieces[row][col].getName();
    }

    public int getPrice(int row, int col) {
        return squarePieces[row][col].getPrice();
    }
}
