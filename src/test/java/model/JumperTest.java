package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import org.junit.Test;
import utils.Location;
import utils.Move;

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

}