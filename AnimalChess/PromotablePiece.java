package animalchess;

import java.util.ArrayList;

public abstract class PromotablePiece extends Piece {

  private boolean isPromoted;

  /**
   * abstract class extended by cat and chick.
   *
   * @param owner  - owner of the piece.
   * @param square - square which piece is placed on.
   */
  public PromotablePiece(Player owner, Square square) {
    super(owner, square); //inherits constructor from Piece
    this.isPromoted = false; //isPromoted set to false by default as cat and chick start game not promoted.
  }

  //implemented by individual pieces to return the possible legal moves.
  public abstract ArrayList<Square> getLegalMoves();

  /**
   * @return isPromoted - true if piece has been promoted by moving in player's promotion zone.
   *                    - false by default at the start or if captured by another player.
   */
  public boolean getIsPromoted() {

    return isPromoted;
  }

  /**
   * updates isPromoted if a piece is promoted. Initiated by move.
   */
  public void promote() {

    this.isPromoted = true;
  }

  /**
   * Moves a piece object to a square. Updates the piece's previous and new square.
   * Differs from Piece.move because PromotablePiece must be promoted if it moves in another
   * the owner's promotion zone.
   * @param toSquare - square object which piece is being moved to.
   */
  @Override
  public void move(Square toSquare) {

    if (toSquare.isPromotionZone(this.getOwner()) && !isPromoted){
      //if piece is not already promoted and is moving in the owner's promotion zone then it is promoted.
      this.promote();
    }
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
   * captures a piece object, initiated by Player.addPieceToHand. Differs from Piece.beCaptured because if a piece is
   * promted then it is demoted when captured.
   * @param capturer - player which is capturing this piece object.
   */
  @Override
  public void beCaptured(Player capturer) {

    this.isPromoted = false;
    this.owner = capturer; //updates the owner of this piece.
    square = this.getSquare();
    square.removePiece(); //removes the piece from it's old square.
    this.square = null;
  }
}
