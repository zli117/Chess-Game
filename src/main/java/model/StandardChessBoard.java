package model;

import java.util.ArrayList;
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
   * @param width  the width of the board
   * @param height the height of the board
   */
  public StandardChessBoard(int height, int width, Class kingClass) {
    super(height, width);
    mKingClass = kingClass;
  }

  /**
   * Check whether the side has been checkmated.
   *
   * @param side The side to check
   * @return True if the side has been checkmated, false otherwise.
   */
  public boolean checkCheckMate(Side side) {
    for (int r = 0; r < getHeight(); ++r) {
      for (int c = 0; c < getWidth(); ++c) {
        Location location = new Location(r, c);
        Piece piece = getPiece(location);
        if (piece != null && piece.getSide() == side) {
          Set<Move> moves = getMoveHints(location);
          if (!moves.isEmpty()) {
            return false;
          }
        }
      }
    }
    return true;
  }

  public boolean checkStaleMate(Side side) {
    Location kingLocation = null;
    for (int r = 0; r < getHeight(); ++r) {
      for (int c = 0; c < getWidth(); ++c) {
        Location location = new Location(r, c);
        Piece piece = getPiece(location);
        if (piece != null && piece.getSide() == side &&
            piece.getClass().equals(mKingClass)) {
          kingLocation = location;
          break;
        }
      }
    }

    Set<Move> kingMove = getMoveHints(kingLocation);
    List<Piece> threateningOpponents = new ArrayList<>();
    for (Piece opponent : getOpponentPieces(side)) {
      Set<Move> intersection = new LinkedHashSet<>(kingMove);
      intersection.retainAll(getMoveHints(opponent.getLocation()));
      if (!intersection.isEmpty()) {
        threateningOpponents.add(opponent);
      }
    }


    return false;
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
    if (noMovesAvailable) {

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
