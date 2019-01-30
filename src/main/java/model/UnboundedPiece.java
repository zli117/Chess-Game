package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import utils.Direction;
import utils.Location;
import utils.Move;

/**
 * Unbounded pieces are those can move along a line. Examples are Queen, Rook
 * etc.
 */
public abstract class UnboundedPiece extends Piece {

  UnboundedPiece(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  /**
   * Return directions to move in a straight line
   */
  protected abstract ArrayList<Direction> getDirections();

  @Override
  public Set<Move> getMovesAndAttacks() {
    LinkedHashSet<Move> allMoves = new LinkedHashSet<>();
    ArrayList<Direction> directions = getDirections();
    for (Direction direction : directions) {
      allMoves.addAll(getMovesInOneDir(direction));
    }
    return Collections.unmodifiableSet(allMoves);
  }

  /**
   * Get the moves in one direction. Which is specified by the increment for row
   * and column
   *
   * @param direction The direction to move along
   */
  private ArrayList<Move> getMovesInOneDir(Direction direction) {
    ArrayList<Move> moves = new ArrayList<>();
    Location current = getLocation();
    ChessBoardBase chessBoard = getChessBoard();
    Location increment;
    do {
      increment = current.getIncrement(direction);
      Move move = new Move(current, increment);
      move.setCanAttack(true);
      moves.add(move);
    } while (chessBoard.checkIsEmpty(increment));
    return moves;
  }

}
