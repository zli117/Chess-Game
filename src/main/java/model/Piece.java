package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import utils.Location;
import utils.Move;
import utils.Vector;


public abstract class Piece {

  private ChessBoardBase mChessBoard;
  private Location mLocation;
  private Side mSide;
  private Set<Move> mAdjustedMoves;
  private boolean mMoved;

  /**
   * Create a chess piece.
   *
   * @param chessBoard A reference to the chess board
   * @param side       The side the piece belongs to
   */
  Piece(ChessBoardBase chessBoard, Side side) {
    mChessBoard = chessBoard;
    mSide = side;
    mAdjustedMoves = new LinkedHashSet<>();
    mMoved = false;
  }

  /**
   * Get the current location.
   */
  public Location getLocation() {
    return mLocation;
  }

  /**
   * Called when the piece has been moved.
   *
   * @param location The new location. Will be null if the piece has been
   *                 captured
   */
  public void setLocation(Location location) {
    if (location != null) {
      if (mLocation != null && !mLocation.equals(location)) {
        mMoved = true;
      }
    }
    mLocation = location;
  }

  /**
   * Check whether the piece has moved.
   */
  public boolean hasMoved() {
    return mMoved;
  }

  /**
   * Get get chess board it belongs to.
   */
  ChessBoardBase getChessBoard() {
    return mChessBoard;
  }

  /**
   * Get which side this piece belongs to.
   */
  public Side getSide() {
    return mSide;
  }

  /**
   * Get the direction if the piece can move in a straight line.
   */
  public List<Vector> getStraightLineMoveDirections() {
    return new ArrayList<>();
  }

  /**
   * Get the offsets for single step moves.
   */
  public List<Vector> getOneStepOffsets() {
    return new ArrayList<>();
  }

  /**
   * Get the moves adjusted by the chess board.
   *
   * @return Unmodifiable set of moves.
   */
  Set<Move> getAdjustedMoves() {
    return Collections.unmodifiableSet(mAdjustedMoves);
  }

  /**
   * Set the set of adjusted moves. The moves can be adjusted by the chess
   * board.
   *
   * @param adjustedMoves The set of adjusted moves
   */
  void setAdjustedMoves(Set<Move> adjustedMoves) {
    mAdjustedMoves = adjustedMoves;
  }

  /**
   * Modify the adjusted moves once all the adjusted moves are decided Should
   * not introduce invalid locations. Default implementation removes any move
   * that could get king checked.
   */
  void modifyAdjustedMoves() {
    ChessBoardBase chessBoard = getChessBoard();
    King king = chessBoard.getKing(getSide());
    Location currLocation = getLocation();
    List<Vector> directionOfMovesToKeep = new ArrayList<>();
    if (king != null && king.getLocation() != null) {
      List<Piece> opponents = chessBoard.getOpponentPieces(getSide());
      for (Piece opponent : opponents) {
        // See if this opponent can attack me
        Location opponentLocation = opponent.getLocation();
        Move move = new Move(opponentLocation, currLocation);
        move.setIsAttack(true);
        // When this function is called, moves are already adjusted
        if (opponent.getAdjustedMoves().contains(move)) {
          // Check to see if its straight line attacks can reach the king
          List<Vector> straightLines = opponent
              .getStraightLineMoveDirections();
          Vector meToOpponent = new Vector(
              currLocation.getRow() - opponentLocation.getRow(),
              currLocation.getCol() - opponentLocation.getCol());
          // Find the direction opponent attacked me
          for (Vector direction : straightLines) {
            if (direction.checkSameDirection(meToOpponent)) {
              Location kingLocation = king.getLocation();
              Location increment = currLocation;
              do {
                increment = increment.getIncrement(direction);
              } while (chessBoard.checkIsEmpty(increment));
              // Oops, king can be attacked if I move away from the line
              if (increment.equals(kingLocation)) {
                // Remove all moves not alone this direction
                directionOfMovesToKeep.add(direction);
              }
            }
          }
        }
      }
    }
    // Remove the moves
    Set<Move> newAdjustedMoves = new LinkedHashSet<>();
    for (Move move : getAdjustedMoves()) {
      Vector moveDirection = move.getDirection();
      boolean aloneTheLine = true;
      for (Vector direction : directionOfMovesToKeep) {
        if (!moveDirection.checkSameDirection(direction)) {
          aloneTheLine = false;
          break;
        }
      }
      if (aloneTheLine) {
        newAdjustedMoves.add(move);
      }
    }
    setAdjustedMoves(newAdjustedMoves);
  }

  /**
   * Get a set of possible moves and attacks. Could contain invalid locations
   * (out of board, occupied etc.). Chessboard will trim out the invalid ones
   * later on.
   */
  public Set<Move> getMovesAndAttacks() {
    LinkedHashSet<Move> moves = new LinkedHashSet<>();
    List<Vector> directions = getStraightLineMoveDirections();
    for (Vector direction : directions) {
      moves.addAll(getMovesInOneDir(direction));
    }

    List<Vector> relativeLocations = getOneStepOffsets();
    Location current = getLocation();
    for (Vector relativeLocation : relativeLocations) {
      Move attackMove = new Move(current,
          current.getIncrement(relativeLocation));
      attackMove.setIsAttack(true);
      moves.add(attackMove);
      // Same reason as unbounded piece. The move is an optional attack.
      Move move = new Move(current,
          current.getIncrement(relativeLocation));
      moves.add(move);
    }

    return Collections.unmodifiableSet(moves);
  }

  /**
   * Get the moves in one direction. Which is specified by the increment for row
   * and column
   *
   * @param direction The direction to move along
   */
  private ArrayList<Move> getMovesInOneDir(Vector direction) {
    ArrayList<Move> moves = new ArrayList<>();
    Location current = getLocation();
    ChessBoardBase chessBoard = getChessBoard();
    Location increment = current;
    do {
      increment = increment.getIncrement(direction);
      Move attackMove = new Move(current, increment);
      attackMove.setIsAttack(true);
      moves.add(attackMove);
      // Add the non attack moves as well. Since attack moves must attack, but
      // unbounded pieces can also have plain moves. In other words, the moves
      // are optional attacks.
      Move move = new Move(current, increment);
      moves.add(move);
    } while (chessBoard.checkIsEmpty(increment));
    return moves;
  }

  /**
   * Get string representation.
   */
  public String toString() {
    return String.format("%s_%s", getClass().getSimpleName(), getSide().name());
  }

  /**
   * Is the piece a ghost. (Used to implement Pawn)
   */
  public boolean isGhost() {
    return false;
  }

  /**
   * Whether the piece can kill a ghost. For standard pieces, only Pawn can kill
   * a ghost piece
   */
  public boolean canKillGhost() {
    return false;
  }

  /**
   * Called when the piece has been killed.
   */
  public void killed() {
  }

}
