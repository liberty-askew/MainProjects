package animalchess;

import java.util.ArrayList;

public class Chick extends PromotablePiece {

  /**
   * inherits constructor from Piece via PromotablePiece for chick object
   *
   * @param square - square which piece is placed on.
   * @param owner  - owner of the piece.
   */

  public Chick(Player owner, Square square) {

    super(owner, square);
  }

  /**
   *implementation of abstract getLegalMoves from Piece.
   *@return ArrayList of possible squares this piece can be moved onto.
   */
  @Override
  public ArrayList<Square> getLegalMoves() {

    ArrayList<Square> moves = new ArrayList<>(); //declares moves array.
    int[][] allMoves; //blank array of all possible moves.
    Square square = this.getSquare();
    Player owner = this.getOwner();
    //pv: player's vertical movement direction, -1 for player 0 and +1 for player 1.
    int pv = 1 - 2 * owner.getPlayerNumber();
    int row0 = square.getRow();
    int col0 = square.getCol();
    final Game game = square.getGame();
    if (!this.getIsPromoted()) {
      //allMoves: list of all possible moves for a chick.
      allMoves = new int[][]{{pv, 0}};
    } else {
      //allMoves: list of all possible moves for a dog / promoted chick.
      allMoves = new int[][]{{pv, 0}, {-pv, 0}, {0, 1}, {0, -1}, {pv, 1}, {pv, -1}};
    }
    for (int[] allMove : allMoves) { //loop searches array list of possible moves.
      int newRow = allMove[0] + row0; //new column of possible move.
      int newCol = allMove[1] + col0; //new row of possible move.
      if (newRow < 6 && newRow >= 0 && newCol >= 0 && newCol < 5) {
        //checks if new square is out of board bounds.
        if (game != null) { //if game exists, takes square from game board.
          moves.add(game.getSquare(newRow, newCol));
        }
        else { //if game does not exist, makes a new square.
          moves.add(new Square(null, newRow, newCol));
        }
      }
    }
    for (int i = moves.size(); i > 0; i--) {
      //loop removes possible move square if the player making the move, already
      //has a piece on that square.
      Square sq_i = moves.get(i - 1);
      if (sq_i.getPiece() != null) {
        if (owner.equals(sq_i.getPiece().getOwner())) {
          moves.remove(i - 1);
        }
      }
    }
    return moves;
  }
}
