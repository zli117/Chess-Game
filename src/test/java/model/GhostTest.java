package model;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import utils.Location;

public class GhostTest {

  @Test
  public void testKilled() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    Pawn pawn = new Pawn(chessBoardBase, Side.White);
    Ghost ghost = new Ghost(chessBoardBase, Side.White, pawn);

    Location pawnLocation = new Location(1, 1);
    Location ghostLocation = new Location(1, 2);

    chessBoardBase.setPiece(pawn, pawnLocation);
    chessBoardBase.setPiece(ghost, ghostLocation);

    chessBoardBase.killPiece(ghostLocation);
    assertNull(pawn.getLocation());
    assertNull(ghost.getLocation());
  }

  @Test
  public void testIsGhost() {
    Ghost ghost = new Ghost(null, Side.White, null);
    assertTrue(ghost.isGhost());
  }

  @Test
  public void testGetMovesAndAttacks() {
    Ghost ghost = new Ghost(null, Side.White, null);
    assertTrue(ghost.getMovesAndAttacks().isEmpty());
  }

}