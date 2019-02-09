package model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import utils.Location;
import utils.Vector;

/**
 * Chess piece for knight.
 */
public class Knight extends Piece {

  private LinkedHashSet<Location> mCachedMoves;

  /**
   * Create a Knight.
   */
  public Knight(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  /**
   * Knight moves one step at a time. Check Wikipedia for knight's moves.
   */
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

  @Override
  public URL getImageResourceUrl() {
    if (getSide() == Side.White) {
      return getClass().getResource("/images/45px-Chess_nlt45.svg.png");
    } else {
      return getClass().getResource("/images/45px-Chess_ndt45.svg.png");
    }
  }

}
