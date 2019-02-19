package model;

import java.util.ArrayList;
import java.util.List;
import utils.Location;
import utils.Move;
import utils.Pair;
import utils.Side;

/**
 * Listens for game state changes.
 */
public class Command implements GameObserverCallBacks {

  private List<Pair<Piece, Location>> mRemovedPieces;
  private List<Pair<Move, Boolean>> mPerformedMoves;
  private boolean mTentative;
  private Move mInitialMove;
  private ChessBoardBase mChessBoard;
  private Side mSideInitiated;

  /**
   * Default constructor. Doesn't need any parameter.
   */
  public Command(Move move, ChessBoardBase chessBoard) {
    mRemovedPieces = new ArrayList<>();
    mPerformedMoves = new ArrayList<>();
    mTentative = false;
    mInitialMove = move;
    mChessBoard = chessBoard;
    mSideInitiated = null;
  }

  /**
   * Set whether the command is for tentative moves.
   */
  void setTentative() {
    mTentative = true;
  }

  /**
   * Check whether the command is associated with a side.
   */
  public boolean hasSide() {
    return mSideInitiated != null;
  }

  /**
   * Get the side the command is associated with. Null if there's no side
   * associated.
   */
  public Side getSide() {
    return mSideInitiated;
  }

  /**
   * Set the side this command is linked with.
   */
  public void setSide(Side side) {
    mSideInitiated = side;
  }

  /**
   * Execute the command.
   *
   * @return True if successful, false otherwise.
   */
  public boolean execute() {
    final boolean previousStatus = mChessBoard.isTentative();
    boolean result = true;
    mChessBoard.setTentative(mTentative);
    mChessBoard.registerObserver(this);
    if (mTentative) {
      mChessBoard.moveWithOutCheck(mInitialMove);
    } else {
      result = mChessBoard.movePiece(mInitialMove);
    }
    mChessBoard.removeObserver(this);
    mChessBoard.setTentative(previousStatus);
    return result;
  }

  /**
   * Undo the moves.
   */
  public void undo() {
    final boolean previousStatus = mChessBoard.isTentative();
    mChessBoard.setTentative(mTentative);
    for (Pair<Move, Boolean> move : mPerformedMoves) {
      Move trackedMove = move.getA();
      boolean firstTimeMoved = move.getB();
      Move inverse = trackedMove.inverseMove();
      Piece movedPiece = mChessBoard.getPiece(inverse.getFrom());
      mChessBoard.moveWithOutCheck(inverse);
      if (firstTimeMoved) {
        movedPiece.resetMoved();
      }
    }
    for (Pair<Piece, Location> removed : mRemovedPieces) {
      mChessBoard.setPiece(removed.getA(), removed.getB());
    }
    mChessBoard.setTentative(previousStatus);
  }

  /**
   * If the piece has moved, track it.
   *
   * @param move The move of the piece (from and to location).
   */
  @Override
  public void pieceMoved(Move move) {
    Piece movedTo = mChessBoard.getPiece(move.getTo());
    mPerformedMoves.add(new Pair<>(move, movedTo.isFirstTimeMoved()));
  }

  /**
   * If the piece has been removed from the board, track it.
   */
  @Override
  public void pieceRemoved(Piece pieceRemoved, Location location) {
    mRemovedPieces.add(new Pair<>(pieceRemoved, location));
  }

  /**
   * Whether this command can track tentative moves. If the command is
   * tentative, then yes, it can. Otherwise it can't.
   */
  @Override
  public boolean canTrackTentative() {
    return mTentative;
  }

}
