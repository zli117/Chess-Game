package model;

import java.util.ArrayList;
import java.util.List;
import utils.Location;
import utils.Move;
import utils.Pair;

/**
 * Listens for game state changes.
 */
public class MoveTracker implements GameObserverCallBacks {

  private List<Pair<Piece, Location>> mRemovedPieces;
  private List<Move> mMoves;

  /**
   * Default constructor. Doesn't need any parameter.
   */
  public MoveTracker() {
    mRemovedPieces = new ArrayList<>();
    mMoves = new ArrayList<>();
  }

  /**
   * If the piece has moved, track it.
   *
   * @param move The move of the piece (from and to location).
   */
  @Override
  public void pieceMoved(Move move) {
    mMoves.add(move);
  }

  /**
   * If the piece has been removed from the board, track it.
   */
  @Override
  public void pieceRemoved(Piece pieceRemoved, Location location) {
    mRemovedPieces.add(new Pair<>(pieceRemoved, location));
  }

  /**
   * Will listen to tentative move. Since this is mostly used for restoring
   * tentative moves.
   */
  @Override
  public boolean includeTentativeMoves() {
    return true;
  }

  /**
   * Retrieve all the removed pieces during tracked period.
   */
  public List<Pair<Piece, Location>> getRemovedPieces() {
    return mRemovedPieces;
  }

  /**
   * Retrieve all the moves during tracked period.
   */
  public List<Move> getMoves() {
    return mMoves;
  }

}
