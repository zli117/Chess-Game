package model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Side;
import utils.Vector;

/**
 * Jumper moves in straight line. Skips a location at a time. For example
 * <table border="1">
 * <tr>
 * <td>  </td> <td>  </td> <td>  </td> <td>  </td> <td>  </td> <td>  </td>
 * <td> x </td> <td>  </td>
 * </tr>
 * <tr>
 * <td>  </td> <td>  </td> <td>  </td> <td>  </td> <td>  </td> <td>  </td>
 * <td> o </td> <td>  </td>
 * </tr>
 * <tr>
 * <td>  </td> <td>  </td> <td>  </td> <td>  </td> <td>  </td> <td>  </td>
 * <td> x </td> <td>  </td>
 * </tr>
 * <tr>
 * <td> o </td> <td> x </td> <td> o </td> <td> x </td> <td> o </td> <td> x
 * </td> <td> J </td> <td> x </td>
 * </tr>
 * </table>
 * o is reachable location. x is not reachable. The Jumper is also a ghost,
 * meaning that normal pieces won't see it and can go pass it. It's also
 * possible that other piece will kick the Jumper off the board if those piece
 * happen to be in the same spot.
 */
public class Jumper extends Piece {

  public Jumper(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  /**
   * The increments for jumper.
   */
  @Override
  public List<Vector> getStraightLineMoveDirections() {
    Vector[] directions = {new Vector(2, 0), new Vector(-2, 0),
        new Vector(0, 2), new Vector(0, -2)};
    return new ArrayList<>(Arrays.asList(directions));
  }

  @Override
  public URL getImageResourceUrl() {
    if (getSide() == Side.White) {
      return getClass().getResource("/images/jumper-white.png");
    } else {
      return getClass().getResource("/images/jumper-black.png");
    }
  }

  @Override
  public boolean isGhost() {
    return true;
  }

}
