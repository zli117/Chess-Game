package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Set;
import javax.imageio.ImageIO;
import org.junit.Test;
import utils.Location;
import utils.Move;

public class SuperQueenTest {

  @Test
  public void testSuperQueenMoves() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    SuperQueen superQueen = new SuperQueen(chessBoardBase, Side.White);
    chessBoardBase.setPiece(superQueen, new Location(4, 4));
    Set<Move> moves = chessBoardBase.getLegalMoves(superQueen.getLocation());
    assertEquals(35, moves.size());
  }

  @Test
  public void testImageUrlIsValid() {
    try {
      SuperQueen superQueen = new SuperQueen(null, Side.White);
      URL imageUrl = superQueen.getImageResourceUrl();
      ImageIO.read(imageUrl);
    } catch (Exception exception) {
      fail();
    }
    try {
      SuperQueen superQueen = new SuperQueen(null, Side.Black);
      URL imageUrl = superQueen.getImageResourceUrl();
      ImageIO.read(imageUrl);
    } catch (Exception exception) {
      fail();
    }
  }

}