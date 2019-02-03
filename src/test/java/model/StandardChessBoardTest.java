package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.SimpleTimeZone;
import org.junit.Test;
import utils.Location;
import utils.Move;

public class StandardChessBoardTest {

  // TODO: Make it parameterized
  @Test
  public void testCheckStaleMate() {
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


    Set<Move> moves = standardChessBoard.getMoveHints(kingW.getLocation());
    for (Move m : moves) {
      System.out.println(m);
    }

    assertFalse(standardChessBoard.checkStaleMate(Side.White));
    assertFalse(standardChessBoard.checkStaleMate(Side.Black));

    Move capturePawn = new Move(kingB.getLocation(), pawnW1.getLocation());
    capturePawn.setIsAttack(true);
    standardChessBoard.movePiece(capturePawn);
    assertTrue(standardChessBoard.checkStaleMate(Side.White));
    assertFalse(standardChessBoard.checkStaleMate(Side.Black));
  }

  @Test
  public void testCheckCheckMate() {
  }

}