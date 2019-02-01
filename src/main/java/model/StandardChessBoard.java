package model;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import utils.Location;
import utils.Move;

public class StandardChessBoard extends ChessBoardBase {

  private Class mKingClass;

  /**
   * Normal chess game setup.
   *
   * @param width the width of the board
   * @param height the height of the board
   */
  public StandardChessBoard(int height, int width, Class kingClass) {
    super(height, width);
    mKingClass = kingClass;
  }

  /**
   * Check whether the game ends with Checkmate or Stalemate. If so, invoke the
   * corresponding call backs.
   *
   * @param side Which side to check for
   */
  public void checkGameEnding(Side side) {
    Location kingLocation = null;
    boolean noMovesAvailable = true;
    for (int r = 0; r < getHeight(); ++r) {
      for (int c = 0; c < getWidth(); ++c) {
        Location location = new Location(r, c);
        Piece piece = getPiece(location);
        if (piece != null && piece.getSide() == side) {
          if (piece.getClass().equals(mKingClass)) {
            kingLocation = location;
          }
          Set<Move> moves = getMoveHints(location);
          if (!moves.isEmpty()) {
            noMovesAvailable = false;
            break;
          }
        }
      }
    }
    // Only check whether king is under check if there's no move available
    if (noMovesAvailable && kingLocation != null) {
      Set<Move> kingMove = getMoveHints(kingLocation);
      boolean isCheckmate = false;
      List<Piece> opponents = getOpponentPieces(side);
      for (Piece opponent : opponents) {
        Set<Move> intersection = new LinkedHashSet<>(kingMove);
        intersection.retainAll(getMoveHints(opponent.getLocation()));
        if (!intersection.isEmpty()) {
          isCheckmate = true;
          break;
        }
      }
      for (GameObserverCallBacks callBacks : getObservers()) {
        if (isCheckmate) {
          callBacks.onCheckmate(side);
        } else {
          callBacks.onStalemate(side);
        }
      }
    }
  }

}
