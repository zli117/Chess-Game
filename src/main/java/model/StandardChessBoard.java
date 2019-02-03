package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import utils.Location;
import utils.Move;
import utils.Vector;

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
   * @return True if there's no legal move available, false otherwise or invalid
   * condition
   */
  public boolean checkStaleMate(Side side) {
    for (Piece piece : getPiecesFromSide(side)) {
      Set<Move> moves = getMoveHints(piece.getLocation());
      if (!moves.isEmpty()) {
        return false;
      }
    }
    King king = getKing(side);
    if (king == null) {
      return false;
    }
    Location kingLocation = king.getLocation();
    for (Piece piece : getOpponentPieces(side)) {
      Set<Move> moves = getMoveHints(piece.getLocation());
      Move attack = new Move(piece.getLocation(), kingLocation);
      attack.attack();
      if (moves.contains(attack)) {
        return false;
      }
    }
    return true;
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
        Location kingLocation = king.getLocation();
        for (Piece opponent : getOpponentPieces(side)) {
          Location opponentLocation = opponent.getLocation();
          Move attack = new Move(opponentLocation, kingLocation);
          attack.attack();
          if (getMoveHints(opponentLocation).contains(attack)) {
            attackers.add(opponent);
          }
        }
        if (attackers.size() == 1) {
          Piece attacker = attackers.get(0);
          Location attackerLocation = attacker.getLocation();
          Vector attackerAndKing = Vector
              .buildVectorFromLocations(attackerLocation, kingLocation);
          // If the attack is a straight, we need to see if any piece could
          // block it.
          List<Location> straightLineLocations = new ArrayList<>();
          for (Vector direction : attacker.getStraightLineMoveDirections()) {
            if (direction.checkSameLocation(attackerAndKing)) {
              System.out.println(direction);
              for (Location increment = attackerLocation
                  .getIncrement(direction);
                  !increment.equals(kingLocation);
                  increment = increment.getIncrement(direction)) {
                straightLineLocations.add(increment);
              }
            }
          }
          for (Piece thisSide : getPiecesFromSide(side)) {
            Location location = thisSide.getLocation();
            Move rescue = new Move(location, attackerLocation);
            rescue.attack();
            Set<Move> moves = getMoveHints(location);
            if (moves.contains(rescue)) {
              return false;
            }
            // If the attacker has straight line attacks, check if there's any
            // piece could block.
            for (Location toBlock : straightLineLocations) {
              Move block = new Move(location, toBlock);
              if (moves.contains(block)) {
                return false;
              }
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
