package model;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;
import utils.Location;
import utils.Move;

/**
 * Ghost piece for en passant implementation. If killed, will kill the
 * associated pawn. Created by pawn on its initial two step move. If not killed,
 * and pawn moved, will be removed from the board.
 */
public class Ghost extends Piece implements GameObserverCallBacks {

  private Pawn mPawn;
  private boolean mInitialPawnMove;

  /**
   * Create a ghost piece with necessary information.
   *
   * @param chessBoard The chess board it's on.
   * @param side       The side it belongs to.
   * @param pawn       The pawn it's associated with.
   */
  public Ghost(ChessBoardBase chessBoard, Side side, Pawn pawn) {
    super(chessBoard, side);
    mPawn = pawn;
    mInitialPawnMove = true;
  }

  @Override
  public void setLocation(Location location) {
    super.setLocation(location);
    // Unregister myself from the watchers
    if (location == null) {
      getChessBoard().removeObserver(this);
    } else {
      // Register myself to the board. Since ghost won't move, it's not a
      // problem
      getChessBoard().registerObserver(this);
    }
  }

  /**
   * Called when killed by an opponent piece.
   */
  @Override
  public void killed() {
    // This will only be called when the ghost is still attached to the pawn.
    // Since if pawn has moved, the ghost will be removed and won't be killed
    // by opponent pawn.
    ChessBoardBase chessBoard = getChessBoard();
    chessBoard.killPiece(mPawn.getLocation());
  }

  /**
   * Is indeed a ghost.
   *
   * @return Always true
   */
  @Override
  public boolean isGhost() {
    return true;
  }

  /**
   * Ghost doesn't have any legal move or attack.
   */
  @Override
  public Set<Move> getMovesAndAttacks() {
    return new LinkedHashSet<>();
  }

  /**
   * For now, there's no image for ghost. No just have null which will be
   * empty.
   */
  @Override
  public URL getImageResourceURL() {
    return null;
  }

  @Override
  public void pieceMoved(Move move) {
    Location myLocation = getLocation();
    ChessBoardBase chessBoard = getChessBoard();
    if (!mInitialPawnMove && myLocation != null && chessBoard != null) {
      // Remove itself if some piece has moved and the ghost is still on the
      // board
      chessBoard.removePiece(myLocation);
    }
    mInitialPawnMove = false;
  }

  @Override
  public void pieceRemoved(Piece pieceRemoved, Location originalLocation) {
  }

  /**
   * Only cares about real moves.
   */
  @Override
  public boolean includeTentativeMoves() {
    return true;
  }

}
