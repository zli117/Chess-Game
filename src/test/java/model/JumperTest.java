package model;

import static org.junit.Assert.assertEquals;

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

}