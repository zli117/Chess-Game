package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import utils.Direction;
import utils.Location;

public class King extends BoundedPiece {

  public King(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  @Override
  ArrayList<Direction> getRelativeLocations() {
    Direction[] relativeLocations = {
        new Direction(1, -1),
        new Direction(1, 0),
        new Direction(1, 1),
        new Direction(0, -1),
        new Direction(0, 1),
        new Direction(-1, -1),
        new Direction(-1, 0),
        new Direction(-1, 1)};
    return new ArrayList<>(Arrays.asList(relativeLocations));
  }

}
