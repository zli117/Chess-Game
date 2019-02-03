package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
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
  private List<GameObserverCallBacks> mObservers;
  private Stack<Piece> mWithHeldPieces;
  private boolean mStateChanged;
  private Map<Side, King> mKings;

  /**
   * Create a chess board.
   *
   * @param height the height of the chess board. Need to be non negative
   * @param width  the width of the chess board. Need to be non negative
   */
  public ChessBoardBase(int height, int width) throws RuntimeException {
    if (width < 0 || height < 0) {
      throw new RuntimeException("Invalid size for chess board");
    }
    mWidth = width;
    mHeight = height;
    mBoard = new Piece[height][width];
    mObservers = new ArrayList<>();
    mWithHeldPieces = new Stack<>();
    mStateChanged = false;
    mKings = new HashMap<>();
  }

  public int getWidth() {
    return mWidth;
  }

  public int getHeight() {
    return mHeight;
  }

  /**
   * Withhold a piece from the board temporarily. Call restoreWithHold to retore
   * the piece.
   *
   * @param location The location of the piece
   * @return The piece withheld
   */
  Piece withHoldPiece(Location location) {
    if (checkValidLocation(location)) {
      Piece piece = mBoard[location.getRow()][location.getCol()];
      if (piece != null) {
        mWithHeldPieces.push(piece);
        mBoard[location.getRow()][location.getCol()] = null;
        mStateChanged = true;
      }
      return piece;
    }
    return null;
  }

  /**
   * Restore the piece in the reverse order of withhold.
   *
   * @return Whether there are still some pieces not restored.
   */
  boolean restoreWithHold() {
    if (!mWithHeldPieces.isEmpty()) {
      Piece top = mWithHeldPieces.pop();
      Location location = top.getLocation();
      mBoard[location.getRow()][location.getCol()] = top;
      if (mWithHeldPieces.isEmpty()) {
        mStateChanged = false;
      }
    }
    return mStateChanged;
  }

  /**
   * Checks whether the location is on the chess board, and if it has a piece.
   *
   * @param location the location.
   * @return True if on the chess board, false otherwise.
   */
  boolean checkValidLocation(Location location) {
    return location.getCol() >= 0 && location.getCol() < mWidth
        && location.getRow() >= 0 && location.getRow() < mHeight;
  }

  /**
   * Check if the location can be captured by the piece. The condition is: 1.
   * The location is valid and has a piece. 2. The piece at the location is not
   * on the same side as the piece. 3. If the location has a ghost and piece can
   * capture a ghost then true. 4. Otherwise if the location has a normal piece
   * then true.
   *
   * @param location The location to capture
   * @param piece    The piece that performs capture
   * @return True if captured, false otherwise.
   */
  boolean checkCanCapture(Location location, Piece piece) {
    Piece target = getPiece(location);
    if (target != null && (getPiece(location).getSide() != piece.getSide())) {
      return (!target.isGhost() || piece.canKillGhost());
    }
    return false;
  }

  boolean checkIsEmpty(Location location) {
    if (checkValidLocation(location)) {
      Piece piece = getPiece(location);
      return (piece == null || piece.isGhost());
    }
    return false;
  }

  /**
   * Add a piece to the board. Will overwrite the existing piece. If the piece
   * is a king, the king won't be registered.
   *
   * @param piece    The chess piece
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

  /**
   * Set the king of a side. If a king of this side already exists, it will be
   * removed.
   *
   * @param king     The king
   * @param location The location
   * @return True if success, false otherwise
   */
  public boolean setKing(King king, Location location) {
    if (checkValidLocation(location)) {
      Side side = king.getSide();
      if (mKings.containsKey(side)) {
        removePiece(mKings.get(side).getLocation());
      }
      mKings.put(side, king);
      return setPiece(king, location);
    }
    return false;
  }

  public King getKing(Side side) {
    if (mKings.containsKey(side)) {
      return mKings.get(side);
    }
    return null;
  }

  /**
   * Get the piece at location.
   *
   * @param location The location
   */
  Piece getPiece(Location location) {
    if (checkValidLocation(location)) {
      return mBoard[location.getRow()][location.getCol()];
    }
    return null;
  }

  /**
   * Get the possible moves and attacks of a piece.
   *
   * @param location The location of the piece
   * @return A set of moves. If the location doesn't have any piece or invalid,
   * return an empty set of moves.
   */
  public Set<Move> getMoveHints(Location location) {
    Piece piece = getPiece(location);
    if (piece == null) {
      return new LinkedHashSet<>();
    }
    if (mStateChanged) {
      computeAdjustedMoves();
      mStateChanged = false;
    }
    piece.modifyAdjustedMoves();
    return piece.getAdjustedMoves();
  }

  /**
   * Query all the possible moves and trim out invalid ones. Adjusted moves will
   * be saved in the piece.
   */
  public void computeAdjustedMoves() {
    for (Piece[] row : mBoard) {
      for (Piece piece : row) {
        if (piece != null) {
          LinkedHashSet<Move> adjustedMoves = new LinkedHashSet<>();
          Set<Move> moves = piece.getMovesAndAttacks();
          for (Move move : moves) {
            Location moveTo = move.getTo();
            // checkValidLocation is implicitly called in checkCanCapture and
            // checkIsEmpty
            if ((move.isAttack() && checkCanCapture(moveTo, piece))
                || (!move.isAttack() && checkIsEmpty(moveTo))) {
              adjustedMoves.add(move);
            }
          }
          piece.setAdjustedMoves(adjustedMoves);
        }
      }
    }
  }

  /**
   * Remove a piece from the board.
   */
  public Piece removePiece(Location location) {
    Piece piece = getPiece(location);
    if (piece != null) {
      mBoard[location.getRow()][location.getCol()] = null;
      piece.setLocation(null);
      for (GameObserverCallBacks observer : mObservers) {
        observer.pieceRemoved(this, piece);
      }
      mStateChanged = true;
    }
    return piece;
  }

  /**
   * Kill a piece: Remove the piece from the board if the location actually has
   * a piece, and invoke the killed() callback on the piece.
   *
   * @param location The location of the piece.
   */
  public void killPiece(Location location) {
    Piece piece = removePiece(location);
    if (piece != null) {
      piece.killed();
    }
  }

  /**
   * Move a piece on the board.
   *
   * @param move Move from a location to a location
   * @return True if moved successful, false otherwise
   */
  public boolean movePiece(Move move) {
    Location fromLocation = move.getFrom();
    Location toLocation = move.getTo();
    Set<Move> possibleMoves = getMoveHints(fromLocation);
    if (possibleMoves.contains(move)) {
      Piece piece = getPiece(move.getFrom());
      // Let the victim piece know it has been killed
      if (move.isAttack()) {
        killPiece(toLocation);
      }
      // removePiece will ignore the piece if it has already been removed from
      // the board
      // mStateChanged is set to true in removePiece
      removePiece(toLocation);
      setPiece(piece, toLocation);
      for (GameObserverCallBacks observer : mObservers) {
        observer.pieceMoved(this, move);
      }
      mBoard[fromLocation.getRow()][fromLocation.getCol()] = null;
      return true;
    }
    return false;
  }

  /**
   * Get all the pieces that could capture pieces on this side.
   */
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
   * Register an observer.
   *
   * @param observer the observer
   */
  public void registerObserver(GameObserverCallBacks observer) {
    mObservers.add(observer);
  }

  /**
   * Get a const list of observers.
   *
   * @return list of observers
   */
  List<GameObserverCallBacks> getObservers() {
    return Collections.unmodifiableList(mObservers);
  }

}
