package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import utils.Location;

public class PieceTest {

  @Test
  public void testLocationAndMove() {
    MockPiece piece = new MockPiece(null, Side.White);
    piece.setLocation(new Location(1, 2));
    assertFalse(piece.hasMoved());
    piece.setLocation(null);
    assertFalse(piece.hasMoved());
    piece.setLocation(new Location(2, 3));
    assertFalse(piece.hasMoved());
    piece.setLocation(new Location(2, 3));
    assertFalse(piece.hasMoved());
    piece.setLocation(new Location(2, 5));
    assertTrue(piece.hasMoved());
    piece.setLocation(null);
    assertTrue(piece.hasMoved());
  }

  @Test
  public void testGetChessBoard() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    MockPiece piece = new MockPiece(chessBoardBase, Side.White);
    assertSame(chessBoardBase, piece.getChessBoard());
  }

  @Test
  public void testGetSide() {
    MockPiece piece = new MockPiece(null, Side.White);
    assertEquals(Side.White, piece.getSide());
    piece = new MockPiece(null, Side.Black);
    assertEquals(Side.Black, piece.getSide());
  }

  @Test
  public void testToString() {
    MockPiece piece = new MockPiece(null, Side.Black);
    assertEquals("MockPiece_Black", piece.toString());
    piece = new MockPiece(null, Side.White);
    assertEquals("MockPiece_White", piece.toString());
  }

  @Test
  public void testCanKillGhost() {
    Queen queen = new Queen(null, Side.Black);
    assertFalse(queen.canKillGhost());
  }

}