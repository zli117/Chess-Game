package model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import utils.Location;
import utils.Move;


public abstract class Piece {

  private ChessBoardBase mChessBoard;
  private Location mLocation;
  private Side mSide;
  private Set<Move> mAdjustedMoves;

  /**
   * Create a chess piece
   *
   * @param chessBoard A reference to the chess board
   * @param side The side the piece belongs to
   */
  Piece(ChessBoardBase chessBoard, Side side) {
    mChessBoard = chessBoard;
    mSide = side;
    mAdjustedMoves = new LinkedHashSet<>();
  }

  /**
   * Get the current location
   */
  public Location getLocation() {
    return mLocation;
  }

  /**
   * Called when the piece has been moved
   *
   * @param location The new location. Will be null if the piece has been
   *                 captured
   */
  public void setLocation(Location location) {
    mLocation = location;
  }

  /**
   * Get get chess board it belongs to
   */
  ChessBoardBase getChessBoard() {
    return mChessBoard;
  }

  /**
   * Get which side this piece belongs to
   */
  public Side getSide() {
    return mSide;
  }

  /**
   * Get the moves adjusted by the chess board
   *
   * @return Unmodifiable set of moves.
   */
  Set<Move> getAdjustedMoves() {
    return Collections.unmodifiableSet(mAdjustedMoves);
  }

  /**
   * Set the set of adjusted moves. The moves can be adjusted by the chess
   * board
   *
   * @param adjustedMoves The set of adjusted moves
   */
  void setAdjustedMoves(Set<Move> adjustedMoves) {
    mAdjustedMoves = adjustedMoves;
  }

  /**
   * Modify the adjusted moves once all the adjusted moves are decided Should
   * not introduce invalid locations
   */
  void modifyAdjustedMoves() {
  }

  /**
   * Get a set of possible moves and attacks. Could contain invalid locations
   * (out of board, occupied etc.). Chessboard will trim out the invalid ones
   * later on
   */
  public abstract Set<Move> getMovesAndAttacks();

  /**
   * Get string representation
   */
  public String toString() {
    return this.getClass().getName();
  }

}
