package model;

import java.util.ArrayList;
import java.util.List;
import utils.Location;
import utils.Move;
import utils.Pair;

/**
 * Listens for game state changes.
 */
public class Command implements GameObserverCallBacks {

  private List<Pair<Piece, Location>> mRemovedPieces;
  private List<Move> mPerformedMoves;
  private boolean mTentative;
  private Move mInitialMove;
  private ChessBoardBase mChessBoard;

  /**
   * Default constructor. Doesn't need any parameter.
   */
  public Command(Move move, ChessBoardBase chessBoard) {
    mRemovedPieces = new ArrayList<>();
    mPerformedMoves = new ArrayList<>();
    mTentative = false;
    mInitialMove = move;
    mChessBoard = chessBoard;
  }

  void setTentative() {
    mTentative = true;
  }

  public void execute() {
    boolean previousStatus = mChessBoard.isTentative();
    mChessBoard.setTentative(mTentative);
    mChessBoard.registerObserver(this);
    if (mTentative) {
      mChessBoard.moveWithOutCheck(mInitialMove);
    } else {
      mChessBoard.movePiece(mInitialMove);
    }
    mChessBoard.removeObserver(this);
    mChessBoard.setTentative(previousStatus);
  }

  public void undo() {
    for (Move trackedMove : mPerformedMoves) {
      Move inverse = trackedMove.inverseMove();
      Piece movedPiece = mChessBoard.getPiece(inverse.getFrom());
      boolean firstTimeMoved = movedPiece.isFirstTimeMoved();
      mChessBoard.moveWithOutCheck(inverse);
      if (firstTimeMoved) {
        movedPiece.resetMoved();
      }
    }
    for (Pair<Piece, Location> removed : mRemovedPieces) {
      mChessBoard.setPiece(removed.getA(), removed.getB());
    }
  }

  /**
   * If the piece has moved, track it.
   *
   * @param move The move of the piece (from and to location).
   */
  @Override
  public void pieceMoved(Move move) {
    mPerformedMoves.add(move);
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
