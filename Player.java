import java.util.Scanner;

/**
 * A class that handles the logic implementation of a Tic-Tac-Toe player
 */
public class Player {
    /**
     * Gets input of coordinates from player,
     * and puts them on the corresponding coordinates of the board.
     * If the coordinates are invalid,
     * then the player will have to put valid coordinates until he succeeds.
     * The function assumes that the user will put numbers as input
     * @param board - A board object on which the game will be played
     * @param mark - A mark that the player can put on board.
     */
    public void playTurn(Board board, Mark mark) {
        Scanner in = new Scanner(System.in);

        // Coordinates of board is an integer with two digits.
        // The left digit is the row of the board and
        // the right digit is the column of the board
        int inputOfCoordinates;
        int row, col;
        boolean putting_mark_success;

        // Put mark on board until succeess
        do {
            inputOfCoordinates = in.nextInt();
            row = inputOfCoordinates / 10 - 1;
            col = inputOfCoordinates % 10 - 1;
            putting_mark_success = board.putMark(mark, row, col);
        } while (!putting_mark_success);
    }
}
