package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Set;
import javax.imageio.ImageIO;
import org.junit.Test;
import utils.Location;
import utils.Move;
import utils.Side;

public class JumperTest {

  @Test
  public void testJumperMoves() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    Jumper jumper = new Jumper(chessBoardBase, Side.White);
    chessBoardBase.setPiece(jumper, new Location(4, 4));
    Set<Move> moves = chessBoardBase.getLegalMoves(jumper.getLocation());
    assertEquals(6, moves.size());
  }

  @Test
  public void testJumperIsGhost() {
    Jumper jumper = new Jumper(null, Side.White);
    assertTrue(jumper.isGhost());
    assertFalse(jumper.canKillGhost());
  }

  @Test
  public void testPassingGhost() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    Jumper jumper = new Jumper(chessBoardBase, Side.White);
    Rook rook = new Rook(chessBoardBase, Side.White);
    chessBoardBase.setPiece(jumper, new Location(4, 4));
    chessBoardBase.setPiece(rook, new Location(4, 2));

    Set<Move> moves = chessBoardBase.getLegalMoves(rook.getLocation());
    assertTrue(
        moves.contains(new Move(rook.getLocation(), jumper.getLocation())));
    assertTrue(moves.contains(
        new Move(rook.getLocation(), jumper.getLocation().getRight())));
  }

  @Test
  public void testImageUrlIsValid() {
    try {
      Jumper jumper = new Jumper(null, Side.White);
      URL imageUrl = jumper.getImageResourceUrl();
      ImageIO.read(imageUrl);
    } catch (Exception exception) {
      fail();
    }
    try {
      Jumper jumper = new Jumper(null, Side.Black);
      URL imageUrl = jumper.getImageResourceUrl();
      ImageIO.read(imageUrl);
    } catch (Exception exception) {
      fail();
    }
  }

}