package animalchess;

import java.util.ArrayList;

public class Player {

  private final String name;
  private final int playerNumber;
  private ArrayList<Piece> hand;
  private boolean won; //false by default. Set to true when capturing Lion.

  /**
   * @param name - name of player.
   * @param playerNumber - must be integer 0 or 1.
   */
  public Player(String name, int playerNumber) {

    this.name = name;
    this.playerNumber = playerNumber;
    this.won = false;
    this.hand = new ArrayList<Piece>();
    if (playerNumber != 0 && playerNumber != 1) {
      //throws IllegalArgumentException if players are not numbered 0 & 1.
      throw new IllegalArgumentException("Players must be numbers 0 & 1.");
    }
  }

  /**
   * @return name of this player.
   */
  public String getName() {

    return this.name;
  }

  /**
   * @return number of this player.
   */
  public int getPlayerNumber() {

    return this.playerNumber;
  }

  /**
   * @return ArrayList of Piece objects in this player's hand.
   */
  public ArrayList<Piece> getHand() {

    return hand;
  }

  /**
   * @param piece - adds this piece to this player's hand.
   */
  public void addPieceToHand(Piece piece) {

    piece.beCaptured(this); //also captures the piece from the board to add to the hand.
    hand.add(piece);
  }

  /**
   * @param piece - piece which is being dropped.
   * @param square - square which the piece is being dropped onto.
   */
  public void dropPiece(Piece piece, Square square) {

    piece.move(square); //updates the piece object.
    hand.remove(piece);
  }

  /**
   * updates won for this player. Initiated by Lion.beCaptured.
   */
  public void winGame() {
    this.won = true;
  }

  /***
   * @return - true if this player has won
   *         - false if this player has not won (can be because they lost or the lion has not been captured).
   */
  public boolean hasWon() {
    return won;
  }
}
