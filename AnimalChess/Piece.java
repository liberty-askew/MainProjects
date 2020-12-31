package animalchess;

import java.util.ArrayList;

public abstract class Piece {

  public Player owner;
  public Square square;

  /**
   * abstract class extended by dog and lion; and cat and chick via promotable piece.
   *
   * @param owner  - owner of the piece.
   * @param square - square which piece is placed on.
   */
  public Piece(Player owner, Square square) {

    if (square.getPiece() == null) {
      this.owner = owner;
      this.square = square;
      square.placePiece(this);
    }
    else {
      //throws error because cannot place a new piece on a square another player already has apiece on.
      throw new IllegalArgumentException("Space is already occupied");
    }
  }

  //implemented by individual pieces to return the possible legal moves.
  public abstract ArrayList<Square> getLegalMoves();


  /**
  *Moves a piece object to a square. Updates the piece's previous and new square.
  *@param toSquare - square object which piece is being moved to.
  */
  public void move(Square toSquare) {

    if (square != null) {
      /*
       *square is null if being dropped from a player's hand therefore do not need to remove the piece.
       *Otherwise need to remove the piece from the old square object.
       */
      square.removePiece();
    }
    if (toSquare.getPiece() != null) {
      if (this.getOwner().equals(toSquare.getPiece().getOwner())) {
        /*
        *throws IllegalArgumentException if player tries to move to a square which they already
        *have a piece on. Will not be an issue if the player takes the square from the getLegalMoves array.
        */
        throw new IllegalArgumentException("Player already has a piece on this square.");
      }
      else {
        //captures the piece if the piece on the new square is owned by the other player.
        owner.addPieceToHand(toSquare.getPiece());
      }
    }
    this.square = toSquare; //updates the square of this piece object.
    square.placePiece(this); //updates the piece of the new square object.
  }

  /**
   * captures a piece object, initiated by Player.addPieceToHand.
   * @param capturer - player which is capturing object.
   */
  public void beCaptured(Player capturer) {

    square.removePiece(); //removes the piece from it's old square.
    this.square = null; //sets this piece's square to null.
    this.owner = capturer; //updates the owner of this piece.
  }

  /**
   * @return - square this piece is on.
   */
  public Square getSquare() {

    return square;
  }

  /**
   * @return - owner of this piece.
   */
  public Player getOwner() {

    return owner;
  }

}
