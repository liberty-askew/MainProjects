package animalchess;

public class Game {

  private static final int HEIGHT = 6;
  private static final int WIDTH  = 5;
  private Player p0;
  private Player p1;
  private static Square[][] board = new Square[HEIGHT][WIDTH]; //6 x 5 array of Square objects. Will be populated by
                                                                // squares on the board.

  /**
   * constructs game and makes 30 square objects for board.
   * @param p0 - player 0.
   * @param p1 - player 1.
   */
  public Game(Player p0, Player p1) {

    this.p0 = p0;
    this.p1 = p1;
    this.makeBoard(); //calls make board method to fill board array with 30 Square objects.
  }

  /***
   * makes a board by filling the board 6 x 5 array with 30 square objects. Such that index location of each square
   * object corresponds to it's row and column position on the board. Initiated when game is constructed.
   */
  private void makeBoard() {

    for (int i = 2; i < HEIGHT - 2; i++) { //makes square objects from non promotion rows [2,3].
      for (int j = 0; j < WIDTH; j++) {
        board[i][j] = new Square(this, i, j);
      }
    }
    for (int j = 0; j < WIDTH; j++) { //makes separate squares in promotion zones when row = [0,1,4,5].
      board[0][j] = new Square(this, 0, j, p1);
      board[1][j] = new Square(this, 1, j, p1);
      board[4][j] = new Square(this, 4, j, p0);
      board[5][j] = new Square(this, 5, j, p0);
    }
  }

  /**
   * @param playerNumber - must be 0 or 1
   * @return player object.
   */
  public Player getPlayer(int playerNumber) {
    if (playerNumber == 0) {
      return p0;
      }
    else if (playerNumber == 1) {
      return p1;
      }
    else {
      throw new IllegalArgumentException("Please use valid player number [0,1]");
      }
  }

  /**
   * @return winning player if Lion has been captured. If Lion has not been captured and no player has yet won then
   * returns null.
   */
  public Player getWinner() {

    Player winner = null;
    if (p0.hasWon()) {
      winner = p0;
    }
    if (p1.hasWon()) {
      winner = p1;
    }
    return winner;
  }

  /**
   * @param row - [0,5]
   * @param col - [0,4]
   * @return square object from game board array.
   */
  public Square getSquare(int row, int col) {

    Square sq = null;
    try {
      sq = board[row][col]; //uses column and row as index to find the square in the multidimensional array.
    }
    catch (Exception e) { //TODO test this
      System.out.println("Square out of board bounds please enter row:[0-5] col:[0-4]");
    }
    return sq;
  }
}
