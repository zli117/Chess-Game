package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import utils.Location;
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

  /**
   * Check whether one side has no legal move.
   *
   * @param side The side
   * @return True if there's no legal move available, false otherwise.
   */
  public boolean checkStaleMate(Side side) {
    List<Piece> sameSide = getPiecesFromSide(side);
    for (Piece piece : sameSide) {
      Set<Move> moves = getMoveHints(piece.getLocation());
      if (!moves.isEmpty()) {
        return true;
      }
    }
    return false;
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
    if (king != null) {
      Set<Move> kingMoves = getMoveHints(king.getLocation());
      // If king doesn't have any legal move, check whether it's under check
      // and could it be resolved.
      if (kingMoves.isEmpty()) {
        List<Piece> attackers = new ArrayList<>();
        for (Piece opponent : getOpponentPieces(side)) {
          Location opponentLocation = opponent.getLocation();
          Move attack = new Move(opponentLocation, king.getLocation());
          attack.setIsAttack(true);
          if (getMoveHints(opponentLocation).contains(attack)) {
            attackers.add(opponent);
          }
        }
        if (attackers.size() == 1) {
          Piece attacker = attackers.get(0);
          Location attackerLocation = attacker.getLocation();
          for (Piece thisSide : getPiecesFromSide(side)) {
            Location location = thisSide.getLocation();
            Move rescue = new Move(location, attackerLocation);
            rescue.setIsAttack(true);
            if (getMoveHints(location).contains(rescue)) {
              return false;
            }
          }
          return true;
        }
        // No way to resolve if there are two attackers
        return attackers.size() > 1;
      }
    }
    return false;
  }

}
