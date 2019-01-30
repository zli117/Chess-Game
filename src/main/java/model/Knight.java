package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import utils.Direction;
import utils.Location;

public class Knight extends BoundedPiece {

  private LinkedHashSet<Location> mCachedMoves;

  public Knight(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  @Override
  ArrayList<Direction> getRelativeLocations() {
    Direction[] relativeLocations = {
        new Direction(-2, 1),
        new Direction(-1, 2),
        new Direction(1, 2),
        new Direction(2, 1),
        new Direction(2, -1),
        new Direction(1, -2),
        new Direction(-2, -1),
        new Direction(-1, -2)};
    return new ArrayList<>(Arrays.asList(relativeLocations));
  }
}
