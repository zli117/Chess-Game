package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Set;
import javax.imageio.ImageIO;
import org.junit.Test;
import utils.Location;
import utils.Move;
import utils.Side;

public class QueenTest {

  @Test
  public void testQueenMoves() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    Queen queen = new Queen(chessBoardBase, Side.White);
    chessBoardBase.setPiece(queen, new Location(4, 4));
    Set<Move> moves = chessBoardBase.getLegalMoves(queen.getLocation());
    assertEquals(27, moves.size());
  }

  @Test
  public void testImageUrlIsValid() {
    try {
      Queen queen = new Queen(null, Side.White);
      URL imageUrl = queen.getImageResourceUrl();
      ImageIO.read(imageUrl);
    } catch (Exception exception) {
      fail();
    }
    try {
      Queen queen = new Queen(null, Side.Black);
      URL imageUrl = queen.getImageResourceUrl();
      ImageIO.read(imageUrl);
    } catch (Exception exception) {
      fail();
    }
  }

}