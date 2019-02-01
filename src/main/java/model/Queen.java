package model;

import java.util.ArrayList;
import java.util.Arrays;
import utils.Direction;

public class Queen extends UnboundedPiece {

  public Queen(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  @Override
  protected ArrayList<Direction> getDirections() {
    Direction[] directions = {
        new Direction(1, 1),
        new Direction(-1, -1),
        new Direction(-1, 1),
        new Direction(1, -1),
        new Direction(1, 0),
        new Direction(-1, 0),
        new Direction(0, 1),
        new Direction(0, -1)};
    return new ArrayList<>(Arrays.asList(directions));
  }

}
