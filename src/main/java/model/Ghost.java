package model;

import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;
import utils.Move;

/**
 * Ghost piece for en passant implementation. If killed, will kill the
 * associated pawn. Created by pawn on its initial two step move. If not killed,
 * and pawn moved, will be removed from the board.
 */
public class Ghost extends Piece {

  private Pawn mPawn;

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

}
