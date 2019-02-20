package model;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Side;
import utils.Vector;

/**
 * A queen combines the power of normal queen and knight.
 * <table border="1">
 * <tr>
 * <td>o  </td> <td>  </td> <td>  </td> <td> o </td> <td>  </td> <td>  </td>
 * <td> o </td> <td>  </td>
 * </tr>
 * <tr>
 * <td>  </td> <td>o  </td> <td> o </td> <td> o </td> <td>o  </td> <td> o </td>
 * <td>  </td> <td>  </td>
 * </tr>
 * <tr>
 * <td>  </td> <td> o </td> <td> o </td> <td> o </td> <td> o </td> <td> o </td>
 * <td>  </td> <td>  </td>
 * </tr>
 * <tr>
 * <td>  </td> <td>  </td> <td>  </td> <td> S </td> <td>  </td> <td>  </td>
 * <td>
 * </td> <td>  </td>
 * </tr>
 * <tr>
 * <td>  </td> <td> o </td> <td> o </td> <td> o </td> <td> o </td> <td> o </td>
 * <td>  </td> <td>  </td>
 * </tr>
 * <tr>
 * <td>  </td> <td> o </td> <td> o </td> <td> o </td> <td> o </td> <td> o </td>
 * <td>  </td> <td>  </td>
 * </tr>
 * <tr>
 * <td> o </td> <td>  </td> <td>  </td> <td> o </td> <td>  </td> <td>  </td>
 * <td> o </td> <td>  </td>
 * </tr>
 * <tr>
 * <td>  </td> <td>  </td> <td>  </td> <td> o </td> <td>  </td> <td>  </td>
 * <td>
 * </td> <td> o </td>
 * </tr>
 * </table>
 */
public class SuperQueen extends Queen {

  /**
   * Construct a super queen.
   */
  public SuperQueen(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  /**
   * Single step moves are the same as knights's.
   */
  @Override
  public List<Vector> getOneStepOffsets() {
    // We have already inherited Queen's straight line moves.
    Vector[] relativeLocations = {new Vector(-2, 1), new Vector(-1, 2),
        new Vector(1, 2), new Vector(2, 1), new Vector(2, -1),
        new Vector(1, -2), new Vector(-2, -1), new Vector(-1, -2)};
    return new ArrayList<>(Arrays.asList(relativeLocations));
  }

  @Override
  public URL getImageResourceUrl() {
    if (getSide() == Side.White) {
      return getClass().getResource("/images/squeen-white.png");
    } else {
      return getClass().getResource("/images/squeen-black.png");
    }
  }

}
