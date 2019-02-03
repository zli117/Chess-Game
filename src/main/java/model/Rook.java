package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Vector;

public class Rook extends Piece {

  public Rook(ChessBoardBase chessBoardBase, Side side) {
    super(chessBoardBase, side);
  }

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
