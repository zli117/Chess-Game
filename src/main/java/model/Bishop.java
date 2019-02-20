package model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Side;
import utils.Vector;

/**
 * Bishop piece.
 */
public class Bishop extends Piece {

  /**
   * Construct a bishop with the chess board and the side it belongs to.
   */
  public Bishop(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  /**
   * Bishop moves in straight lines. This function returns the direction
   * (increment of each step) of the movement.
   */
  @Override
  public List<Vector> getStraightLineMoveDirections() {
    Vector[] directions = {new Vector(1, 1), new Vector(-1, -1),
        new Vector(-1, 1), new Vector(1, -1)};
    return new ArrayList<>(Arrays.asList(directions));
  }

  @Override
  public URL getImageResourceUrl() {
    if (getSide() == Side.White) {
      return getClass().getResource("/images/45px-Chess_blt45.svg.png");
    } else {
      return getClass().getResource("/images/45px-Chess_bdt45.svg.png");
    }
  }

}
