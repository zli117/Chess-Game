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

  void setTentative() {
    mTentative = true;
  }

  public boolean hasSide() {
    return mSideInitiated != null;
  }

  public Side getSide() {
    return mSideInitiated;
  }

  public void setSide(Side side) {
    mSideInitiated = side;
  }

  public boolean execute() {
    boolean previousStatus = mChessBoard.isTentative();
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

  public void undo() {
    boolean previousStatus = mChessBoard.isTentative();
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

  @Override
  public boolean canTrackTentative() {
    return mTentative;
  }

}
