package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.Set;
import javax.imageio.ImageIO;
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

    // Restore to board
    chessBoardBase.setPiece(pawn, newLocation);
    assertNotNull(chessBoardBase.getPiece(ghostLocation));

    // Move the pawn. Ghost should be removed
    chessBoardBase.movePiece(new Move(newLocation, newLocation.getAbove()));
    assertNull(chessBoardBase.getPiece(ghostLocation));

    // Create a new pawn. If other piece has moved, the ghost should be removed.
    pawn = new Pawn(chessBoardBase, Side.White);
    chessBoardBase.setPiece(pawn, new Location(7, 7));
    chessBoardBase.movePiece(
        new Move(pawn.getLocation(), pawn.getLocation().getAbove().getAbove()));
    assertNotNull(chessBoardBase.getPiece(pawn.getLocation().getBelow()));
    // Now move the other pawn
    chessBoardBase.movePiece(
        new Move(newLocation.getAbove(), newLocation.getAbove().getAbove()));
    assertNull(chessBoardBase.getPiece(pawn.getLocation().getBelow()));

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
    Location downLocation = new Location(3, 4);
    chessBoardBase.setPiece(upPawn, upLocation);
    chessBoardBase.setPiece(downPawn, downLocation);

    assertTrue(chessBoardBase
        .movePiece(new Move(upLocation, upLocation.getAbove().getAbove())));
    System.out.println(chessBoardBase);
    Move captureMove = new Move(downPawn.getLocation(), upLocation.getAbove());
    captureMove.attack();
    assertNotNull(upPawn.getLocation());
    assertTrue(chessBoardBase.movePiece(captureMove));
    assertNull(upPawn.getLocation());
    assertTrue(chessBoardBase.getPiece(captureMove.getTo()) instanceof Pawn);
  }

  @Test
  public void testPieceMoved() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    Ghost ghost = new Ghost(chessBoardBase, Side.White, null);
    chessBoardBase.setPiece(ghost, new Location(3, 2));
    ghost.pieceMoved(new Move(new Location(1, 2), new Location(3, 4)));
    assertNotNull(ghost.getLocation());
    ghost.pieceMoved(new Move(new Location(1, 2), new Location(3, 4)));
    assertNull(ghost.getLocation());
    ghost.pieceMoved(new Move(new Location(1, 2), new Location(3, 4)));
  }

  @Test
  public void testInvalidEnPassant() {
    // Attack from the other pawn is not immediately after
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
    System.out.println(chessBoardBase);
    assertTrue(chessBoardBase
        .movePiece(new Move(downLocation, downLocation.getBelow())));
    System.out.println(chessBoardBase);

    Move captureMove = new Move(downPawn.getLocation(), upLocation.getAbove());
    captureMove.attack();
    assertNotNull(upPawn.getLocation());
    assertFalse(chessBoardBase.movePiece(captureMove));
    System.out.println(chessBoardBase);
  }

  @Test
  public void testImageUrlIsValid() {
    try {
      Pawn pawn = new Pawn(null, Side.White);
      URL imageURL = pawn.getImageResourceUrl();
      ImageIO.read(imageURL);
    } catch (Exception exception) {
      fail();
    }
    try {
      Pawn pawn = new Pawn(null, Side.Black);
      URL imageURL = pawn.getImageResourceUrl();
      ImageIO.read(imageURL);
    } catch (Exception exception) {
      fail();
    }
  }

}