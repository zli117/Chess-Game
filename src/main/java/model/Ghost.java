package model;

import java.util.LinkedHashSet;
import java.util.Set;
import utils.Move;

public class Ghost extends Piece {

  private Pawn mPawn;

  public Ghost(ChessBoardBase chessBoard, Side side, Pawn pawn) {
    super(chessBoard, side);
    mPawn = pawn;
  }

  @Override
  public void killed() {
    // This will only be called when the ghost is still attached to the pawn.
    // Since if pawn has moved, the ghost will be removed and won't be killed
    // by opponent pawn.
    ChessBoardBase chessBoard = getChessBoard();
    chessBoard.killPiece(mPawn.getLocation());
  }

  @Override
  public boolean isGhost() {
    return true;
  }

  @Override
  public Set<Move> getMovesAndAttacks() {
    return new LinkedHashSet<>();
  }

}
