/**
 * A class implementing the logic of the board of Tic-Tac-Toe
 */

public class Board {
    // The size of a dimension of Board
    public static final int SIZE = 4;
    // Tells what is the streak that a mark needs, to win the game
    public static final int WIN_STREAK = 3;

    // Assuming that we are moving over a 2d matrix,
    // DELTAS is an array of delta movements in some direction.
    // If delta is 0, then there is no movement
    private static final int[] DELTAS = { -1, 0, 1 };
    private static final int NO_MOVEMENT = 0;
    // A two dimensional matrix that stores marks on board
    private Mark[][] marks;
    private Mark gameWinner;
    private int numberOfBlankMarks;
    // The streak that the mark that is played currently has
    private int streakOfMark;

    /**
     * Constructor of Board object
     */
    public Board () {
        this.gameWinner = Mark.BLANK;
        this.numberOfBlankMarks = SIZE * SIZE;
        // Initialize a 2D array of blank marks
        initialize_marks();
        // Assuming the board is full of blank marks
        this.streakOfMark = 0;
    }


    /**
     * A private method that initializes a two-dimensional
     * matrix with the size of SIZE * SIZE, and
     * fills it with blank marks.
     */
    private void initialize_marks() {
        marks = new Mark[SIZE][SIZE];

        for(int i = 0; i < marks.length; i++) {
            for (int j = 0; j < marks[i].length; j++) {
                marks[i][j] = Mark.BLANK;
            }
        }
    }

    /**
     * Put a mark on the board in given x and y coordinates.
     * @param mark - The mark to put
     * @param row - The row in the board where mark will be put.
     * @param col - The column in the board where mark will be put.
     * @return boolean - True if the operation succeeded, and false otherwise.
     */
    public boolean putMark(Mark mark, int row, int col) {
        // Make sure that the mark is put in valid indexes,
        // that the mark that is valid (X or O),
        // and that the current cell is unmarked
        boolean operationSuccess = inBoundariesOfBoard(row, col) &&
                (mark != Mark.BLANK) && (marks[row][col] == Mark.BLANK);

        if (operationSuccess) {
            marks[row][col] = mark;
            numberOfBlankMarks--;

            // Counts the streak of mark
            determineStreak(mark, row, col);
            if(gameEnded()) {
                gameWinner = (streakOfMark >= WIN_STREAK) ? mark : Mark.BLANK;
            }
        }

        return operationSuccess;
    }

    /**
     * Checks if given row and column coordinates are valid
     * @param row - Coordinate of rows
     * @param col - Coordinate of columns
     * @return boolean - true if coordinates are valid, and false otherwise
     */
    private boolean inBoundariesOfBoard (int row, int col) {
        return (0 <= row) && (row < SIZE) && (0 <= col) && (col < SIZE);
    }

    /**
     * Determines the streak of a mark that was put in specific coordinates
     * on the board
     * @param mark - A mark that was played currently
     * @param row - An integer representing the number of a row
     * @param col - An integer representing the number of a column
     */
    private void determineStreak(Mark mark, int row, int col) {
        // Nullify streak of mark so it can be calculated
        // in the method calculateStreakOfMark for the current mark
        streakOfMark = 0;

        int currentStreakOfMark;

        // For each direction, calculate streakOfMark in each direction.
        // A direction can be: up, up right, right, down right, down,
        // down left, left and up left
        for(int rowDelta : DELTAS) {
            for (int colDelta : DELTAS) {
                // Count streak of mark only when there is a movement
                if (rowDelta != NO_MOVEMENT || colDelta != NO_MOVEMENT) {
                    currentStreakOfMark = calculateStreakOfMark(mark,
                            row, col, rowDelta, colDelta);

                    // Take the maximum value between streakOfMark and
                    // currentStreakOfMark, and assign it to streakOfMark
                    
                    if (currentStreakOfMark > streakOfMark) {
                        streakOfMark = currentStreakOfMark;
                    }
                }
            }
        }
    }

    /**
     * Calculates streak of mark in a given direction,
     * which is expressed by rowDelta and colDelta.
      * @param mark - A mark of which its streak will be calculated
     * @param row - Row position of the mark on board
     * @param col - Column position of the mark on board
     * @param rowDelta - Row coordinate of the direction to move from
     *                 coordinates (row, col)
     * @param colDelta - Column coordinate of the direction to move from
     *                 coordinates (row, col).
     * @return int - An integer specifying the streak of mark
     */
    private int calculateStreakOfMark(Mark mark, int row, int col,
                                             int rowDelta, int colDelta) {
        // Count the streak of mark, if the count is in direction
        // of [rowDelta, colDelta]
        int streakOfMarkWithDirection = countMarkInDirection(row, col,
                rowDelta, colDelta, mark);
        // Count the streak of mark, if the count is in direction
        // of [-rowDelta, -colDelta]
        int streakOfMarkAgainstDirection = countMarkInDirection(row, col,
                -rowDelta, -colDelta, mark);

        // Add the two variables and subtract the sum by one to
        // avoid overlapping of mark in position (row, col)
        return streakOfMarkWithDirection + streakOfMarkAgainstDirection - 1;
    }

    /**
     * Read mark in specified row and column of the board.
     * If coordinates are legal, then return the mark in the given position.
     * Otherwise return a blank mark.
     * @param row - Row of the board from which to read mark
     * @param col - Column of the board from which to read mark
     * @return Mark object in given row and column
     */
    public Mark getMark(int row, int col) {
        boolean coordinatesAreLegal = inBoundariesOfBoard(row, col);
        return coordinatesAreLegal ? marks[row][col] : Mark.BLANK;
    }


    /**
     * Check if the game ended by a win of a mark, or by a draw
     * @return boolean - A boolean value that checks if the game ended.
     */
    public boolean gameEnded() {
        return (streakOfMark >= WIN_STREAK || numberOfBlankMarks == 0);
    };

    /**
     * Counts streak of a mark in a given direction
     * @param row - Row position of the mark on board
     * @param col - Column position of the mark on board
     * @param rowDelta - Row coordinate of the direction to move from
     *                 coordinates (row, col)
     * @param colDelta - Row coordinate of the direction to move from
     *                 coordinates (row, col)
     * @param mark - Mark of which its streak will be counted
     * @return int - An integer specifying the streak of the mark in
     *              direction [rowDelta, colDelta]
     */
    private int countMarkInDirection(int row, int col, int rowDelta,
                                     int colDelta, Mark mark) {
        int count = 0;
        while(inBoundariesOfBoard(row, col) && marks[row][col] == mark) {
            count++;
            row += rowDelta;
            col += colDelta;
        }
        return count;
    }

    /**
     * Getter method of winner of Tic-Tac-Toe game
     * @return Mark object representing winner of the game.
     * A blank mark represents that there is no winner to the game.
     */
    public Mark getWinner() { return gameWinner; }
}
