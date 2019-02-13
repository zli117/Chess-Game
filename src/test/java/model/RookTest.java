package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Set;
import javax.imageio.ImageIO;
import org.junit.Test;
import utils.Location;
import utils.Move;

public class RookTest {

  @Test
  public void testQueenMoves() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    Rook rook = new Rook(chessBoardBase, Side.White);
    chessBoardBase.setPiece(rook, new Location(4, 4));
    Set<Move> moves = chessBoardBase.getLegalMoves(rook.getLocation());
    assertEquals(14, moves.size());
  }

  @Test
  public void testImageUrlIsValid() {
    try {
      Rook rook = new Rook(null, Side.White);
      URL imageUrl = rook.getImageResourceUrl();
      ImageIO.read(imageUrl);
    } catch (Exception exception) {
      fail();
    }
    try {
      Rook rook = new Rook(null, Side.Black);
      URL imageUrl = rook.getImageResourceUrl();
      ImageIO.read(imageUrl);
    } catch (Exception exception) {
      fail();
    }
  }

}