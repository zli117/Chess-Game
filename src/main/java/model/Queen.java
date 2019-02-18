package model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Side;
import utils.Vector;

/**
 * Queen chess piece. Queen moves in straight lines.
 */
public class Queen extends Piece {

  /**
   * Create a queen.
   */
  public Queen(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  /**
   * The straight line movements in each step.
   */
  @Override
  public List<Vector> getStraightLineMoveDirections() {
    Vector[] directions = {
        new Vector(1, 1),
        new Vector(-1, -1),
        new Vector(-1, 1),
        new Vector(1, -1),
        new Vector(1, 0),
        new Vector(-1, 0),
        new Vector(0, 1),
        new Vector(0, -1)};
    return new ArrayList<>(Arrays.asList(directions));
  }

  @Override
  public URL getImageResourceUrl() {
    if (getSide() == Side.White) {
      return getClass().getResource("/images/45px-Chess_qlt45.svg.png");
    } else {
      return getClass().getResource("/images/45px-Chess_qdt45.svg.png");
    }
  }

}
