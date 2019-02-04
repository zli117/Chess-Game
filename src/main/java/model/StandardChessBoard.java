package model;

import java.util.Set;
import utils.Move;

public class StandardChessBoard extends ChessBoardBase {

  /**
   * Normal chess game setup.
   *
   * @param width  the width of the board
   * @param height the height of the board
   */
  public StandardChessBoard(int height, int width) {
    super(height, width);
  }

  private boolean checkHasLegalMoves(Side side) {
    for (Piece piece : getPiecesFromSide(side)) {
      Set<Move> moves = getLegalMoves(piece.getLocation());
      if (!moves.isEmpty()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check whether one side has no legal move.
   *
   * @param side The side
   * @return True if there's no legal move available, false otherwise or invalid
   * condition
   */
  public boolean checkStaleMate(Side side) {
    King king = getKing(side);
    return king != null && !checkHasLegalMoves(side)
        && !checkKingPossiblyUnderCheck(side);
  }

  /**
   * Check whether one side has been checkmated.
   *
   * @param side The side
   * @return True if the side has been checkmated, false otherwise or status is
   * invalid.
   */
  public boolean checkCheckMate(Side side) {
    King king = getKing(side);
    return king != null && !checkHasLegalMoves(side)
        && checkKingPossiblyUnderCheck(side);
  }

}
