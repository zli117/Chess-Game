package model;

import java.util.ArrayList;
import java.util.Arrays;
import utils.Direction;

public class Rook extends UnboundedPiece {

  public Rook(ChessBoardBase chessBoardBase, Side side) {
    super(chessBoardBase, side);
  }

  @Override
  protected ArrayList<Direction> getDirections() {
    Direction[] directions = {
        new Direction(1, 0),
        new Direction(-1, 0),
        new Direction(0, 1),
        new Direction(0, -1)};
    return new ArrayList<>(Arrays.asList(directions));
  }

}
