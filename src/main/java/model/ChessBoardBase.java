package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import utils.Location;
import utils.Move;

/**
 * Base class of the chess board. Responsible for basic operations such as
 * adding pieces, moving pieces and deciding winner.
 */
public class ChessBoardBase {

  private int mWidth;
  private int mHeight;
  private Piece[][] mBoard;
  private ArrayList<GameObserverCallBacks> mObservers;
  private boolean mStateChanged;

  /**
   * Create a chess board
   *
   * @param width the width of the chess board. Need to be non negative
   * @param height the height of the chess board. Need to be non negative
   */
  public ChessBoardBase(int width, int height) throws RuntimeException {
    if (width < 0 || height < 0) {
      throw new RuntimeException("Invalid size for chess board");
    }
    mWidth = width;
    mHeight = height;
    mBoard = new Piece[height][width];
    mObservers = new ArrayList<>();
    mStateChanged = false;
  }

  public int getWidth() {
    return mWidth;
  }

  public int getHeight() {
    return mHeight;
  }

  /**
   * Checks whether the location is on the chess board, and if it has a piece.
   *
   * @param location the location.
   * @return True if on the chess board, false otherwise.
   */
  private boolean checkValidLocation(Location location) {
    return location.getCol() >= 0 && location.getCol() < mWidth
        && location.getRow() >= 0 && location.getRow() < mHeight;
  }

  private boolean checkCanCapture(Location location, Piece piece) {
    return getPiece(location) != null
        && (getPiece(location).getSide() != piece.getSide());
  }

  boolean checkIsEmpty(Location location) {
    return checkValidLocation(location) && getPiece(location) == null;
  }

  /**
   * Add a piece to the board. Will overwrite the existing piece
   *
   * @param piece The chess piece
   * @param location The location
   * @return True if success, false otherwise
   */
  public boolean setPiece(Piece piece, Location location) {
    if (checkValidLocation(location)) {
      mBoard[location.getRow()][location.getCol()] = piece;
      piece.setLocation(location);
      mStateChanged = true;
      return true;
    }
    return false;
  }

  Piece getPiece(Location location) {
    if (checkValidLocation(location)) {
      return mBoard[location.getRow()][location.getCol()];
    }
    return null;
  }

  public Set<Move> getMoveHints(Location location) {
    Piece piece = getPiece(location);
    if (piece == null) {
      return new LinkedHashSet<>();
    }
    if (mStateChanged) {
      computeAdjustedMoves();
      piece.modifyAdjustedMoves();
      mStateChanged = false;
    }
    return piece.getAdjustedMoves();
  }

  /**
   * Query all the possible moves and trim out invalid ones. Adjusted moves will
   * be saved in the piece.
   */
  private void computeAdjustedMoves() {
    for (Piece[] row : mBoard) {
      for (Piece piece : row) {
        if (piece != null) {
          LinkedHashSet<Move> adjustedMoves = new LinkedHashSet<>();
          Set<Move> moves = piece.getMovesAndAttacks();
          for (Move move : moves) {
            Location moveTo = move.getTo();
            // checkValidLocation is implicitly called in checkCanCapture and
            // checkIsEmpty
            if ((move.canAttack() && checkCanCapture(moveTo, piece))
                || checkIsEmpty(moveTo)) {
              adjustedMoves.add(move);
            }
          }
          piece.setAdjustedMoves(adjustedMoves);
        }
      }
    }
  }

  /**
   * Remove a piece from the board
   *
   * @param location The location of the piece
   * @return The piece being removed. If the location is empty or invalid, null
   * will be returned
   */
  public Piece removePiece(Location location) {
    Piece piece = getPiece(location);
    if (piece != null) {
      mBoard[location.getRow()][location.getCol()] = null;
      piece.setLocation(null);
    }
    return piece;
  }

  private void killPiece(Location location) {
    Piece piece = getPiece(location);
    if (piece != null) {
      mBoard[location.getRow()][location.getCol()] = null;
      piece.setLocation(null);
      mStateChanged = true;
      for (GameObserverCallBacks observer : mObservers) {
        observer.pieceKilled(this, piece);
      }
    }
  }

  /**
   * Move a piece on the board
   *
   * @param move Move from a location to a location
   * @return True if moved successful, false otherwise
   */
  public boolean movePiece(Move move) {
    Location fromLocation = move.getFrom();
    Location toLocation = move.getTo();
    Set<Move> possibleMoves = getMoveHints(fromLocation);
    if (possibleMoves.contains(move)) {
      if (move.canAttack() && !checkIsEmpty(toLocation)) {
        killPiece(toLocation);
      }
      Piece piece = getPiece(move.getFrom());
      // mStateChanged is set to true in setPiece
      setPiece(piece, toLocation);
      mBoard[fromLocation.getRow()][fromLocation.getCol()] = null;
    }
    return false;
  }

  public List<Piece> getOpponentPieces(Side side) {
    ArrayList<Piece> opponents = new ArrayList<>();
    for (Piece[] row : mBoard) {
      for (Piece piece : row) {
        if (piece != null && piece.getSide() != side) {
          opponents.add(piece);
        }
      }
    }
    return Collections.unmodifiableList(opponents);
  }

  /**
   * Register an observer
   *
   * @param observer the observer
   */
  public void registerObserver(GameObserverCallBacks observer) {
    mObservers.add(observer);
  }

  /**
   * Get a const list of observers
   *
   * @return list of observers
   */
  List<GameObserverCallBacks> getObservers() {
    return Collections.unmodifiableList(mObservers);
  }
}
