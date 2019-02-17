package model;

import utils.Location;
import utils.Move;

/**
 * Callback interface for game observers. Observer can subscribe to the change
 * of game status such as piece movement and removal of piece.
 */
public interface GameObserverCallBacks {

  /**
   * Called when a piece has moved board.
   *
   * @param move The move of the piece (from and to location).
   */
  void pieceMoved(Move move);

  /**
   * Called when a piece is removed from the board. Possible reasons of a piece
   * removal include: undo, piece killed, piece overridden (currently only
   * possible for ghost).
   */
  void pieceRemoved(Piece pieceRemoved, Location originalLocation);

  /**
   * Whether the observer can track tentative moves.
   */
  boolean canTrackTentative();

}
