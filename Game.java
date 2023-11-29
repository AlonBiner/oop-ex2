/**
 * A class which manages the game of Tic-Tac-Toe.
 */

public class Game {
    private Player playerX;
    private Player playerO;
    private Renderer renderer;

    /**
     * Constructor of game object
     * @param playerX - Player that marks X
     * @param playerO - Player that marks O
     * @param renderer - A Renderer object which is responsible for
     *                 display pf the board
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
    }

    /**
     * The method that runs the Game tic-tac-toe
     * @return - A mark representing the winner of the game
     */
    public Mark run() {
        Board board = new Board();
        int counter = 0;
        Player[] players = { playerX, playerO };
        Mark[] marks = { Mark.X, Mark.O };

        Player currentPlayer;
        Mark currentMark;

        // Display start board
        renderer.renderBoard(board);

        // In each turn of the game,
        // a player plays a turn and
        // the resulting board is shown at the end of the turn.
        while (!board.gameEnded()) {
            currentPlayer = players[counter];
            currentMark = marks[counter];
            currentPlayer.playTurn(board, currentMark);
            renderer.renderBoard(board);
            counter = (counter + 1) % 2;
        }

        // Print the winner of the game.
        // In case of a draw "BLANK" will be returned
        System.out.println(board.getWinner());
        return board.getWinner();
    }

    /**
     * The main function which runs the entire program
     * @param args - Arguments from the command line
     */
    public static void main(String[] args) {
        // Initialize game object and its dependencies and
        // run the game
        Player playerX = new Player();
        Player playerO = new Player();
        Renderer renderer = new Renderer();
        Game game = new Game(playerX, playerO, renderer);
        game.run();
    }
}
