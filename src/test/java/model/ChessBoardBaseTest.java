package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Set;
import org.junit.Test;
import utils.Location;
import utils.Move;

public class ChessBoardBaseTest {

  @Test
  public void testGetWidth() {
    ChessBoardBase chessBoard = new ChessBoardBase(10, 10);
    assertEquals(10, chessBoard.getWidth());
    try {
      chessBoard = new ChessBoardBase(10, -10);
      fail();
    } catch (RuntimeException exception) {
      assertTrue(true);
    }
    chessBoard = new ChessBoardBase(10, 0);
    assertEquals(0, chessBoard.getWidth());
  }

  @Test
  public void testGetHeight() {
    ChessBoardBase chessBoard = new ChessBoardBase(10, 10);
    assertEquals(10, chessBoard.getHeight());
    try {
      chessBoard = new ChessBoardBase(-10, 10);
      fail();
    } catch (RuntimeException exception) {
      assertTrue(true);
    }
    chessBoard = new ChessBoardBase(0, 10);
    assertEquals(0, chessBoard.getHeight());
  }

  @Test
  public void testWithHoldPieceAndRestoreWithHold() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    MockPiece mockPiece = new MockPiece(chessBoardBase, Side.Black);
    Location pieceLocation = new Location(5, 5);
    chessBoardBase.setPiece(mockPiece, pieceLocation);
    assertEquals(pieceLocation, mockPiece.getLocation());
    Piece piece = chessBoardBase.withHoldPiece(pieceLocation);
    assertTrue(mockPiece.isAlive());
    assertEquals(pieceLocation, mockPiece.getLocation());
    assertSame(mockPiece, piece);
    assertNull(chessBoardBase.getPiece(pieceLocation));
    assertFalse(chessBoardBase.restoreWithHold());
    assertTrue(mockPiece.isAlive());

    MockPiece mockPiece1 = new MockPiece(chessBoardBase, Side.Black);
    Location piece1Location = new Location(0, 0);
    chessBoardBase.setPiece(mockPiece1, piece1Location);
    piece = chessBoardBase.withHoldPiece(piece1Location);
    assertSame(mockPiece1, piece);
    piece = chessBoardBase.withHoldPiece(pieceLocation);
    assertSame(mockPiece, piece);
    assertTrue(chessBoardBase.restoreWithHold());
    assertFalse(chessBoardBase.restoreWithHold());
    assertFalse(chessBoardBase.restoreWithHold());

    assertNull(chessBoardBase.withHoldPiece(new Location(2, 2)));
    assertNull(chessBoardBase.withHoldPiece(new Location(20, 2)));
  }

  @Test
  public void testCheckValidLocation() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    assertTrue(chessBoardBase.checkValidLocation(new Location(0, 0)));
    assertTrue(chessBoardBase.checkValidLocation(new Location(5, 5)));
    assertFalse(chessBoardBase.checkValidLocation(new Location(25, 5)));
    assertFalse(chessBoardBase.checkValidLocation(new Location(25, 5)));
    assertFalse(chessBoardBase.checkValidLocation(new Location(5, -5)));
    assertFalse(chessBoardBase.checkValidLocation(new Location(-5, 5)));
  }

  @Test
  public void testCheckCanCapture() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    MockPiece mockPiece = new MockPiece(chessBoardBase, Side.Black);
    Location location = new Location(2, 3);
    chessBoardBase.setPiece(mockPiece, location);
    MockPiece mockAttacker = new MockPiece(chessBoardBase, Side.White);
    assertTrue(chessBoardBase.checkCanCapture(location, mockAttacker));
    mockPiece.setIsGhost(true);
    assertFalse(chessBoardBase.checkCanCapture(location, mockAttacker));
    mockAttacker.setCanKillGhost(true);
    assertTrue(chessBoardBase.checkCanCapture(location, mockAttacker));
    assertFalse(
        chessBoardBase.checkCanCapture(new Location(0, 0), mockAttacker));
    assertFalse(
        chessBoardBase.checkCanCapture(new Location(-10, 0), mockAttacker));
    assertFalse(chessBoardBase.checkCanCapture(location, mockPiece));
  }

  @Test
  public void testCheckIsEmpty() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    MockPiece mockPiece = new MockPiece(chessBoardBase, Side.Black);
    Location pieceLocation = new Location(5, 5);
    chessBoardBase.setPiece(mockPiece, pieceLocation);
    assertFalse(chessBoardBase.checkIsEmpty(pieceLocation));
    // Make sure we can't see ghost
    mockPiece.setIsGhost(true);
    assertTrue(chessBoardBase.checkIsEmpty(pieceLocation));
    mockPiece.setIsGhost(false);
    assertFalse(chessBoardBase.checkIsEmpty(pieceLocation));

    assertTrue(chessBoardBase.checkIsEmpty(new Location(0, 0)));
    assertFalse(chessBoardBase.checkIsEmpty(new Location(10, 10)));
  }

  @Test
  public void testSetPiece() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    MockPiece mockPiece = new MockPiece(chessBoardBase, Side.Black);
    Location validLocation = new Location(5, 5);
    assertTrue(chessBoardBase.setPiece(mockPiece, validLocation));
    Location invalidLocation = new Location(20, 20);
    assertFalse(chessBoardBase.setPiece(mockPiece, invalidLocation));
    assertSame(mockPiece, chessBoardBase.getPiece(validLocation));
  }

  @Test
  public void testGetMoveHints() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    MockPiece mockPieceWhite1 = new MockPiece(chessBoardBase, Side.White);
    MockPiece mockPieceWhite2 = new MockPiece(chessBoardBase, Side.White);
    MockPiece mockPieceBlack1 = new MockPiece(chessBoardBase, Side.Black);

    Location mockW1Location = new Location(5, 3);
    Location mockW2Location = new Location(5, 6);
    Location mockB1Location = new Location(2, 3);
    chessBoardBase.setPiece(mockPieceWhite1, mockW1Location);
    chessBoardBase.setPiece(mockPieceBlack1, mockB1Location);
    chessBoardBase.setPiece(mockPieceWhite2, mockW2Location);

    Set<Move> moves = chessBoardBase.getLegalMoves(mockW1Location);
    Move move = new Move(mockW1Location, mockB1Location);
    move.attack();
    assertTrue(moves.contains(move));
    move = new Move(mockW1Location, mockW2Location);
    assertFalse(moves.contains(move));
    move.attack();
    assertFalse(moves.contains(move));

    assertTrue(mockPieceWhite1.hasModifiedAdjustedMoves());
    assertTrue(mockPieceWhite1.hasSetAdjustedMoves());
    assertFalse(mockPieceWhite2.hasModifiedAdjustedMoves());
    assertTrue(mockPieceWhite2.hasSetAdjustedMoves());
    assertFalse(mockPieceBlack1.hasModifiedAdjustedMoves());
    assertTrue(mockPieceBlack1.hasSetAdjustedMoves());

    move = new Move(mockW2Location, mockW1Location);
    moves = chessBoardBase.getLegalMoves(mockW2Location);
    assertFalse(moves.contains(move));
    move.attack();
    assertFalse(moves.contains(move));

    assertEquals(0, chessBoardBase.getLegalMoves(new Location(0, 0)).size());
  }

  @Test
  public void testRemovePiece() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    MockCallBack callBack = new MockCallBack();
    chessBoardBase.registerObserver(callBack);
    MockPiece mockPiece = new MockPiece(chessBoardBase, Side.Black);
    Location location = new Location(1, 2);
    chessBoardBase.setPiece(mockPiece, location);
    assertNotNull(chessBoardBase.removePiece(location));
    assertNull(mockPiece.getLocation());

    assertNull(chessBoardBase.removePiece(location));
    assertNull(chessBoardBase.removePiece(new Location(10, 10)));

    assertSame(mockPiece, callBack.getRemoved());
  }

  @Test
  public void testKillPiece() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    MockCallBack callBack = new MockCallBack();
    chessBoardBase.registerObserver(callBack);
    MockPiece mockPiece = new MockPiece(chessBoardBase, Side.Black);
    Location location = new Location(1, 2);
    chessBoardBase.setPiece(mockPiece, location);
    chessBoardBase.killPiece(location);
    chessBoardBase.killPiece(location);
    assertNull(mockPiece.getLocation());

    assertSame(mockPiece, callBack.getRemoved());
    assertFalse(mockPiece.isAlive());
  }

  @Test
  public void testMovePiece() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    MockCallBack callBack = new MockCallBack();
    chessBoardBase.registerObserver(callBack);
    MockPiece mockPieceWhite1 = new MockPiece(chessBoardBase, Side.White);
    MockPiece mockPieceWhite2 = new MockPiece(chessBoardBase, Side.White);
    MockPiece mockPieceBlack1 = new MockPiece(chessBoardBase, Side.Black);

    Location mockW1Location = new Location(5, 3);
    Location mockW2Location = new Location(5, 6);
    Location mockB1Location = new Location(2, 3);
    chessBoardBase.setPiece(mockPieceWhite1, mockW1Location);
    chessBoardBase.setPiece(mockPieceBlack1, mockB1Location);
    chessBoardBase.setPiece(mockPieceWhite2, mockW2Location);

    Move move = new Move(mockW1Location, mockB1Location);
    move.attack();
    assertTrue(chessBoardBase.movePiece(move));

    assertEquals(mockB1Location, mockPieceWhite1.getLocation());
    assertNull(mockPieceBlack1.getLocation());
    assertFalse(mockPieceBlack1.isAlive());

    assertEquals(move, callBack.getMove());
    assertSame(mockPieceBlack1, callBack.getRemoved());

    move = new Move(mockB1Location, new Location(0, 0));
    assertFalse(chessBoardBase.movePiece(move));

    move = new Move(mockW2Location, mockW1Location);
    assertTrue(chessBoardBase.movePiece(move));
    assertEquals(mockW1Location, mockPieceWhite2.getLocation());
  }

  @Test
  public void testGetOpponentPieces() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    MockPiece mockPieceWhite1 = new MockPiece(chessBoardBase, Side.White);
    MockPiece mockPieceWhite2 = new MockPiece(chessBoardBase, Side.White);
    MockPiece mockPieceBlack1 = new MockPiece(chessBoardBase, Side.Black);

    Location mockW1Location = new Location(5, 3);
    Location mockW2Location = new Location(5, 6);
    Location mockB1Location = new Location(2, 3);
    chessBoardBase.setPiece(mockPieceWhite1, mockW1Location);
    chessBoardBase.setPiece(mockPieceBlack1, mockB1Location);
    chessBoardBase.setPiece(mockPieceWhite2, mockW2Location);

    List<Piece> whitePieces = chessBoardBase.getOpponentPieces(Side.Black);
    List<Piece> blackPieces = chessBoardBase.getOpponentPieces(Side.White);

    assertEquals(1, blackPieces.size());
    assertEquals(2, whitePieces.size());

    assertTrue(blackPieces.contains(mockPieceBlack1));
    assertTrue(whitePieces.contains(mockPieceWhite1));
    assertTrue(whitePieces.contains(mockPieceWhite2));
  }

  @Test
  public void testGetObservers() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    MockCallBack callBack = new MockCallBack();

    assertEquals(0, chessBoardBase.getObservers().size());
    chessBoardBase.registerObserver(callBack);
    assertEquals(1, chessBoardBase.getObservers().size());
    assertTrue(chessBoardBase.getObservers().contains(callBack));
  }

  @Test
  public void testSetKing() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    King king = new King(chessBoardBase, Side.Black);

    assertFalse(chessBoardBase.setKing(king, new Location(8, 9)));
    assertTrue(chessBoardBase.setKing(king, new Location(5, 5)));
    assertTrue(chessBoardBase.setKing(king, new Location(4, 5)));
    assertEquals(new Location(4, 5), king.getLocation());
    assertSame(king, chessBoardBase.getKing(Side.Black));
  }

  @Test
  public void testRemoveKing() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    King king = new King(chessBoardBase, Side.Black);

    assertTrue(chessBoardBase.setKing(king, new Location(5, 5)));
    assertNull(chessBoardBase.removeKing(Side.White));
    assertNotNull(chessBoardBase.removeKing(Side.Black));
    assertNull(chessBoardBase.getKing(Side.White));
  }

  @Test
  public void testBaseFunctions() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    assertFalse(chessBoardBase.checkStaleMate(Side.White));
    assertFalse(chessBoardBase.checkCheckMate(Side.White));
    MockPiece mockPiece = new MockPiece(chessBoardBase, Side.White);
    chessBoardBase.setPiece(mockPiece, new Location(2, 3));
    assertEquals(Side.White,
        chessBoardBase.getSideOfLocation(new Location(2, 3)));
    assertNull(chessBoardBase.getSideOfLocation(new Location(2, 5)));
  }

}