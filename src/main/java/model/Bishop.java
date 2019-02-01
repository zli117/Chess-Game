package model;

import java.util.ArrayList;
import java.util.Arrays;
import utils.Direction;

public class Bishop extends UnboundedPiece {

  public Bishop(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  @Override
  protected ArrayList<Direction> getDirections() {
    Direction[] directions = {
        new Direction(1, 1),
        new Direction(-1, -1),
        new Direction(-1, 1),
        new Direction(1, -1)};
    return new ArrayList<>(Arrays.asList(directions));
  }

}
