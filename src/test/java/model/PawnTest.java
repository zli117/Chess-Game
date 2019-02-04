package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import org.junit.Test;
import utils.Location;
import utils.Move;

public class PawnTest {

  @Test
  public void testSetLocation() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    Pawn pawn = new Pawn(chessBoardBase, Side.White);
    chessBoardBase.setPiece(pawn, new Location(6, 1));

    // Adding ghost
    Location newLocation = new Location(4, 1);
    Location ghostLocation = new Location(5, 1);
    chessBoardBase.setPiece(pawn, newLocation);
    assertEquals(newLocation, pawn.getLocation());
    Piece ghost = chessBoardBase.getPiece(ghostLocation);
    assertNotNull(ghost);
    assertTrue(ghost.isGhost());

    // Remove from the board
    chessBoardBase.removePiece(newLocation);
    assertNull(pawn.getLocation());
    assertNull(ghost.getLocation());

    // Restore to board
    chessBoardBase.setPiece(ghost, ghostLocation);
    chessBoardBase.setPiece(pawn, newLocation);
    assertNotNull(chessBoardBase.getPiece(ghostLocation));

    // Move the pawn. Ghost should be removed
    pawn.setLocation(new Location(3, 1));
    assertNull(chessBoardBase.getPiece(ghostLocation));

    pawn = new Pawn(chessBoardBase, Side.White);
    chessBoardBase.setPiece(pawn, new Location(6, 6));
    assertTrue(chessBoardBase
        .movePiece(new Move(new Location(6, 6), new Location(5, 6))));
    assertNull(chessBoardBase.getPiece(new Location(6, 6)));
    assertNull(chessBoardBase.getPiece(new Location(4, 6)));
  }

  @Test
  public void testIsMovingUp() {
    Pawn pawn = new Pawn(null, Side.White);
    assertTrue(pawn.isMovingUp());
    pawn.setMovingUp(false);
    assertFalse(pawn.isMovingUp());
  }

  @Test
  public void testGetMovesAndAttacks() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    Pawn upPawn = new Pawn(chessBoardBase, Side.White);
    Pawn downPawn = new Pawn(chessBoardBase, Side.White);
    downPawn.setMovingUp(false);

    Location upPawnLocation = new Location(6, 6);
    Location downPawnLocation = new Location(1, 6);
    chessBoardBase.setPiece(upPawn, upPawnLocation);
    chessBoardBase.setPiece(downPawn, downPawnLocation);

    Set<Move> upMoves = upPawn.getMovesAndAttacks();
    Set<Move> downMoves = downPawn.getMovesAndAttacks();

    assertEquals(4, upMoves.size());
    assertEquals(4, downMoves.size());

    Pawn blocker = new Pawn(chessBoardBase, Side.White);
    chessBoardBase.setPiece(blocker, new Location(5, 6));
    upMoves = upPawn.getMovesAndAttacks();
    assertEquals(3, upMoves.size());
  }

  @Test
  public void testCanKillGhost() {
    Pawn pawn = new Pawn(null, Side.White);
    assertTrue(pawn.canKillGhost());
  }

  @Test
  public void testEnPassant() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    Pawn upPawn = new Pawn(chessBoardBase, Side.White);
    Pawn downPawn = new Pawn(chessBoardBase, Side.Black);
    downPawn.setMovingUp(false);

    Location upLocation = new Location(5, 3);
    Location downLocation = new Location(2, 4);
    chessBoardBase.setPiece(upPawn, upLocation);
    chessBoardBase.setPiece(downPawn, downLocation);

    assertTrue(chessBoardBase
        .movePiece(new Move(upLocation, upLocation.getAbove().getAbove())));
    assertTrue(chessBoardBase
        .movePiece(new Move(downLocation, downLocation.getBelow())));

    Move captureMove = new Move(downPawn.getLocation(), upLocation.getAbove());
    captureMove.attack();
    assertNotNull(upPawn.getLocation());
    assertTrue(chessBoardBase.movePiece(captureMove));
    assertNull(upPawn.getLocation());
    assertTrue(chessBoardBase.getPiece(captureMove.getTo()) instanceof Pawn);
  }

}