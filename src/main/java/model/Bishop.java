package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Vector;

public class Bishop extends Piece {

  public Bishop(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  @Override
  public List<Vector> getStraightLineMoveDirections() {
    Vector[] directions = {
        new Vector(1, 1),
        new Vector(-1, -1),
        new Vector(-1, 1),
        new Vector(1, -1)};
    return new ArrayList<>(Arrays.asList(directions));
  }

}
