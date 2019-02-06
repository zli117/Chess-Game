package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Vector;

/**
 * Rook chess piece.
 */
public class Rook extends Piece {

  /**
   * Create a rook.
   */
  public Rook(ChessBoardBase chessBoardBase, Side side) {
    super(chessBoardBase, side);
  }

  /**
   * Rook moves in straight lines. Returns the increment of movements in each
   * step.
   */
  @Override
  public List<Vector> getStraightLineMoveDirections() {
    Vector[] directions = {
        new Vector(1, 0),
        new Vector(-1, 0),
        new Vector(0, 1),
        new Vector(0, -1)};
    return new ArrayList<>(Arrays.asList(directions));
  }

}
