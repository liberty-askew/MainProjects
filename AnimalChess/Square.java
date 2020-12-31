package animalchess;

public class Square {

  private Game game;
  private final int row;
  private final int col;
  private Player promotesPlayer;
  private Piece piece;

  /**
   * initiates square objects from rows 2 and 3, which are not player promotion zones.
   *  Makes piece object when initiated if has a piece starting on it.
   * @param game - game which this square if part of. Can be null if no game.
   * @param row - row which this square is from. final because does not change.
   * @param col - column which this square is from. final because does not change.
   */
  public Square(Game game, int row, int col) {
    this.game = game;
    this.row = row;
    this.col = col;
    if (game != null) {
      // if game is not null then populates the squares with the chicks in their starting positions. Chicks are the
      // only pieces starting on this row because this Square is only used for rows 2 and 3.
      if (col == 1) {
        this.piece = new Chick(game.getPlayer(row % 2), this);
      }
      if (col == 2) {
        this.piece = new Chick(game.getPlayer(row % 2), this);
      }
      if (col == 3) {
        this.piece = new Chick(game.getPlayer(row % 2), this);
      }
    }
  }

  /**
   * polymorphism of square above for rows which are in player promotion zones.
   * @param game - game which this square if part of. Can be null if no game.
   * @param row - row which this square is from. final because does not change.
   * @param col - column which this square is from. final because does not change.
   * @param promotesPlayer - player which is promoted for in these rows. Rows 4 & 5: promote player 0.
   *                                                                     Rows 0 & 1: promote player 1.
   */
  public Square(Game game, int row, int col, Player promotesPlayer) {
    this.game = game;
    this.row = row;
    this.col = col;
    this.promotesPlayer = promotesPlayer;
    if (game != null) {
      // if game is not null then populates the squares with the chicks in their starting positions. Chicks are the
      // only pieces starting on this row because this Square is only used for rows 2 and 3.
      if (row % 5 == 0) { //true for rows 0 and 5.
        if (col == 0) {
          this.piece = new Cat(game.getPlayer(row % 4), this);
        }
        if (col == 1) {
          this.piece = new Dog(game.getPlayer(row % 4), this);
        }
        if (col == 2) {
          this.piece = new Lion(game.getPlayer(row % 4), this);
        }
        if (col == 3) {
          this.piece = new Dog(game.getPlayer(row % 4), this);
        }
        if (col == 4) {
          this.piece = new Cat(game.getPlayer(row % 4), this);
        }
      }
      }
  }

  /***
   * Places piece on this square if the square is empty.
   * @param piece - piece object which is placed on this square.
   */
  public void placePiece(Piece piece) {
    if (this.getPiece() == null) {
      this.piece = piece;
    }
    else {
      //Cannot place piece on a non-empty square.
      throw new IllegalArgumentException("Square is already occupied");
    }
  }

  /**
   * removes piece from a square and updates piece for this square. Initiated by Piece.move.
   */
  public void removePiece() {
    this.piece = null;
  }

  /**
   *
   * @param player - player which this is a promotion zone of.
   * @return true - for player 0 and square in rows 4 or 5.
   *         true - for player 1 and square in rows 0 or 1.
   *         false - otherwise.
   */
  public boolean isPromotionZone(Player player) {
    boolean promoted = false;
    if (promotesPlayer == player) {
      promoted = true;
    }
    return promoted;
  }

  /**
   * @return piece for this square.
   */
  public Piece getPiece() {
    return piece;
  }

  /**
   * @return game this square is part of.
   */
  public Game getGame() {
    return game;
  }

  /**
   * @return row this square is on [0-5].
   */
  public int getRow() {
    return row;
  }

  /**
   * @return column this square is on [0-4].
   */
  public int getCol() {
    return col;
  }
}
