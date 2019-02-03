package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import utils.Location;
import utils.Vector;

public class Knight extends Piece {

  private LinkedHashSet<Location> mCachedMoves;

  public Knight(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  @Override
  public List<Vector> getOneStepOffsets() {
    Vector[] relativeLocations = {
        new Vector(-2, 1),
        new Vector(-1, 2),
        new Vector(1, 2),
        new Vector(2, 1),
        new Vector(2, -1),
        new Vector(1, -2),
        new Vector(-2, -1),
        new Vector(-1, -2)};
    return new ArrayList<>(Arrays.asList(relativeLocations));
  }

}
