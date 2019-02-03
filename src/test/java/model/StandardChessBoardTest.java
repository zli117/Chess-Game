package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import utils.Location;
import utils.Move;

public class StandardChessBoardTest {

  // TODO: Make it parameterized
  @Test
  public void testCheckStaleMate1() {
    // Anand versus Kramnik
    StandardChessBoard standardChessBoard = new StandardChessBoard(8, 8);
    King kingW = new King(standardChessBoard, Side.White);
    King kingB = new King(standardChessBoard, Side.Black);
    Pawn pawnW1 = new Pawn(standardChessBoard, Side.White);
    Pawn pawnW2 = new Pawn(standardChessBoard, Side.White);
    Pawn pawnB1 = new Pawn(standardChessBoard, Side.Black);
    Pawn pawnB2 = new Pawn(standardChessBoard, Side.Black);
    pawnB1.setMovingUp(false);
    pawnB2.setMovingUp(false);

    standardChessBoard.setPiece(pawnB1, new Location(2, 5));
    standardChessBoard.setPiece(pawnB2, new Location(1, 6));
    standardChessBoard.setPiece(pawnW1, new Location(3, 5));
    standardChessBoard.setPiece(pawnW2, new Location(4, 7));
    standardChessBoard.setKing(kingB, new Location(4, 4));
    standardChessBoard.setKing(kingW, new Location(3, 7));

    assertFalse(standardChessBoard.checkStaleMate(Side.White));
    assertFalse(standardChessBoard.checkStaleMate(Side.Black));

    Move capturePawn = new Move(kingB.getLocation(), pawnW1.getLocation());
    capturePawn.setIsAttack(true);
    standardChessBoard.movePiece(capturePawn);
    assertTrue(standardChessBoard.checkStaleMate(Side.White));
    assertFalse(standardChessBoard.checkStaleMate(Side.Black));

    assertFalse(standardChessBoard.checkCheckMate(Side.Black));
    assertFalse(standardChessBoard.checkCheckMate(Side.White));

    System.out.println(standardChessBoard);
  }

  @Test
  public void testCheckStaleMate2() {
    // Simple Example 1: Burn vs. Pilsbury
    StandardChessBoard standardChessBoard = new StandardChessBoard(8, 8);
    King kingW = new King(standardChessBoard, Side.White);
    King kingB = new King(standardChessBoard, Side.Black);
    Pawn pawnW = new Pawn(standardChessBoard, Side.White);
    standardChessBoard.setKing(kingW, new Location(2, 5));
    standardChessBoard.setKing(kingB, new Location(0, 5));
    standardChessBoard.setPiece(pawnW, new Location(1, 5));

    assertTrue(standardChessBoard.checkStaleMate(Side.Black));
    assertFalse(standardChessBoard.checkStaleMate(Side.White));

    assertFalse(standardChessBoard.checkCheckMate(Side.Black));
    assertFalse(standardChessBoard.checkCheckMate(Side.White));

    System.out.println(standardChessBoard);
  }

  @Test
  public void testCheckCheckMate1() {
    // Support mate
    StandardChessBoard standardChessBoard = new StandardChessBoard(8, 8);
    King kingW = new King(standardChessBoard, Side.White);
    King kingB = new King(standardChessBoard, Side.Black);
    Queen queenW = new Queen(standardChessBoard, Side.White);
    standardChessBoard.setKing(kingW, new Location(2, 2));
    standardChessBoard.setKing(kingB, new Location(2, 0));
    standardChessBoard.setPiece(queenW, new Location(2, 1));

    assertTrue(standardChessBoard.checkCheckMate(Side.Black));
    assertFalse(standardChessBoard.checkCheckMate(Side.White));

    assertFalse(standardChessBoard.checkStaleMate(Side.White));
    assertFalse(standardChessBoard.checkStaleMate(Side.Black));

    System.out.println(standardChessBoard);
  }

  @Test
  public void testCheckCheckMate2() {
    // Checkmate with a rook
    StandardChessBoard standardChessBoard = new StandardChessBoard(8, 8);
    King kingW = new King(standardChessBoard, Side.White);
    King kingB = new King(standardChessBoard, Side.Black);
    Rook rookW = new Rook(standardChessBoard, Side.White);
    standardChessBoard.setKing(kingW, new Location(3, 5));
    standardChessBoard.setKing(kingB, new Location(3, 7));
    standardChessBoard.setPiece(rookW, new Location(7, 7));

    assertTrue(standardChessBoard.checkCheckMate(Side.Black));
    assertFalse(standardChessBoard.checkCheckMate(Side.White));

    assertFalse(standardChessBoard.checkStaleMate(Side.White));
    assertFalse(standardChessBoard.checkStaleMate(Side.Black));

    System.out.println(standardChessBoard);
  }

  @Test
  public void testCheckCheckMate3() {
    // Resolvable (shouldn't checkmate)
    StandardChessBoard standardChessBoard = new StandardChessBoard(8, 8);
    King kingW = new King(standardChessBoard, Side.White);
    King kingB = new King(standardChessBoard, Side.Black);
    Rook rookW = new Rook(standardChessBoard, Side.White);
    Rook rookB = new Rook(standardChessBoard, Side.Black);
    standardChessBoard.setKing(kingW, new Location(3, 5));
    standardChessBoard.setKing(kingB, new Location(3, 7));
    standardChessBoard.setPiece(rookW, new Location(7, 7));
    standardChessBoard.setPiece(rookB, new Location(7, 0));

    assertFalse(standardChessBoard.checkCheckMate(Side.Black));
    assertFalse(standardChessBoard.checkCheckMate(Side.White));

    assertFalse(standardChessBoard.checkStaleMate(Side.White));
    assertFalse(standardChessBoard.checkStaleMate(Side.Black));

    System.out.println(standardChessBoard);

    // But now we got 2 attackers. Not resolvable anymore (black checkmated)
    Queen queenW = new Queen(standardChessBoard, Side.White);
    standardChessBoard.setPiece(queenW, new Location(0, 7));
    assertTrue(standardChessBoard.checkCheckMate(Side.Black));
    assertFalse(standardChessBoard.checkCheckMate(Side.White));

    assertFalse(standardChessBoard.checkStaleMate(Side.White));
    assertFalse(standardChessBoard.checkStaleMate(Side.Black));

    System.out.println(standardChessBoard);
  }

  @Test
  public void testCheckCheckMate4() {
    // Invalid (no king)
    StandardChessBoard standardChessBoard = new StandardChessBoard(8, 8);
    King kingW = new King(standardChessBoard, Side.White);
    Rook rookW = new Rook(standardChessBoard, Side.White);
    standardChessBoard.setKing(kingW, new Location(3, 5));
    standardChessBoard.setPiece(rookW, new Location(7, 7));

    assertFalse(standardChessBoard.checkCheckMate(Side.Black));
    assertFalse(standardChessBoard.checkCheckMate(Side.White));

    assertFalse(standardChessBoard.checkStaleMate(Side.White));
    assertFalse(standardChessBoard.checkStaleMate(Side.Black));

    System.out.println(standardChessBoard);
  }


}