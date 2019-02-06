package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Vector;

/**
 * Bishop piece.
 */
public class Bishop extends Piece {

  /**
   * Construct a bishop with the chess board and the side it belongs to.
   */
  public Bishop(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  /**
   * Bishop moves in straight lines. This function returns the direction
   * (increment of each step) of the movement.
   */
  @Override
  public List<Vector> getStraightLineMoveDirections() {
    Vector[] directions = {
        new Vector(1, 1),
        new Vector(-1, -1),
        new Vector(-1, 1),
        new Vector(1, -1)};
    return new ArrayList<>(Arrays.asList(directions));
  }

}
