package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import org.junit.Test;
import utils.Location;
import utils.Move;


public class KingTest {

  @Test
  public void testCastlingLeft() {
    // Test moving rook when castling
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    King king = new King(chessBoardBase, Side.Black);
    Rook rookLeft = new Rook(chessBoardBase, Side.Black);
    Rook rookRight = new Rook(chessBoardBase, Side.Black);
    Queen queen = new Queen(chessBoardBase, Side.White);

    Location kingLocation = new Location(6, 4);
    chessBoardBase.setPiece(king, kingLocation);
    chessBoardBase.setPiece(rookLeft, new Location(6, 0));
    chessBoardBase.setPiece(rookRight, new Location(6, 7));
    chessBoardBase.setPiece(queen, new Location(5, 5));

    Location leftCastling = new Location(6, 2);
    Location rightCastling = new Location(6, 6);
    Set<Move> moves = chessBoardBase.getMoveHints(kingLocation);

    assertTrue(moves.contains(new Move(kingLocation, leftCastling)));
    assertFalse(moves.contains(new Move(kingLocation, rightCastling)));

    chessBoardBase.movePiece(new Move(kingLocation, leftCastling));
    assertEquals(new Location(6, 2), king.getLocation());
    assertTrue(chessBoardBase.getPiece(new Location(6, 2)) instanceof King);

    assertEquals(new Location(6, 3), rookLeft.getLocation());
    assertTrue(chessBoardBase.getPiece(new Location(6, 3)) instanceof Rook);
  }

  @Test
  public void testCastlingRight() {
    // Test moving rook when castling
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    King king = new King(chessBoardBase, Side.Black);
    Rook rookLeft = new Rook(chessBoardBase, Side.Black);
    Rook rookRight = new Rook(chessBoardBase, Side.Black);
    Queen queen = new Queen(chessBoardBase, Side.White);

    Location kingLocation = new Location(6, 4);
    chessBoardBase.setPiece(king, kingLocation);
    chessBoardBase.setPiece(rookLeft, new Location(6, 0));
    chessBoardBase.setPiece(rookRight, new Location(6, 7));
    chessBoardBase.setPiece(queen, new Location(5, 3));

    Location leftCastling = new Location(6, 2);
    Location rightCastling = new Location(6, 6);
    Set<Move> moves = chessBoardBase.getMoveHints(kingLocation);

    assertFalse(moves.contains(new Move(kingLocation, leftCastling)));
    assertTrue(moves.contains(new Move(kingLocation, rightCastling)));

    chessBoardBase.movePiece(new Move(kingLocation, rightCastling));
    assertEquals(new Location(6, 6), king.getLocation());
    assertTrue(chessBoardBase.getPiece(new Location(6, 6)) instanceof King);

    assertEquals(new Location(6, 5), rookRight.getLocation());
    assertTrue(chessBoardBase.getPiece(new Location(6, 5)) instanceof Rook);

    chessBoardBase.getMoveHints(new Location(6, 6));
  }

  @Test
  public void testNoCastling() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    King king = new King(chessBoardBase, Side.Black);
    Rook rookLeft = new Rook(chessBoardBase, Side.Black);
    Rook rookRight = new Rook(chessBoardBase, Side.Black);
    Queen queen1 = new Queen(chessBoardBase, Side.White);
    Queen queen2 = new Queen(chessBoardBase, Side.Black);

    Location kingLocation = new Location(6, 4);
    chessBoardBase.setPiece(king, kingLocation);
    chessBoardBase.setPiece(rookLeft, new Location(6, 0));
    chessBoardBase.setPiece(rookRight, new Location(6, 7));
    chessBoardBase.setPiece(queen1, new Location(6, 6));
    chessBoardBase.setPiece(queen2, new Location(6, 1));

    Set<Move> moves = chessBoardBase.getMoveHints(kingLocation);
    assertFalse(moves.contains(new Move(kingLocation, new Location(6, 2))));
    assertFalse(moves.contains(new Move(kingLocation, new Location(6, 6))));

    assertNotNull(chessBoardBase.removePiece(queen1.getLocation()));
    assertNotNull(chessBoardBase.removePiece(queen2.getLocation()));

    moves = chessBoardBase.getMoveHints(kingLocation);
    assertTrue(moves.contains(new Move(kingLocation, new Location(6, 2))));
    assertTrue(moves.contains(new Move(kingLocation, new Location(6, 6))));

    chessBoardBase.movePiece(
        new Move(rookLeft.getLocation(), rookLeft.getLocation().getAbove()));
    chessBoardBase.movePiece(
        new Move(rookLeft.getLocation(), rookLeft.getLocation().getBelow()));
    chessBoardBase.movePiece(
        new Move(rookRight.getLocation(), rookRight.getLocation().getAbove()));
    chessBoardBase.movePiece(
        new Move(rookRight.getLocation(), rookRight.getLocation().getBelow()));

    moves = chessBoardBase.getMoveHints(kingLocation);
    assertFalse(moves.contains(new Move(kingLocation, new Location(6, 2))));
    assertFalse(moves.contains(new Move(kingLocation, new Location(6, 6))));

    rookLeft = new Rook(chessBoardBase, Side.White);
    rookRight = new Rook(chessBoardBase, Side.White);
    chessBoardBase.setPiece(rookLeft, new Location(6, 0));
    chessBoardBase.setPiece(rookRight, new Location(6, 7));

    moves = chessBoardBase.getMoveHints(kingLocation);
    assertFalse(moves.contains(new Move(kingLocation, new Location(6, 2))));
    assertFalse(moves.contains(new Move(kingLocation, new Location(6, 6))));
  }

  @Test
  public void testGetMoveAndModify() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    King king = new King(chessBoardBase, Side.Black);
    Rook rook1 = new Rook(chessBoardBase, Side.White);
    Rook rook2 = new Rook(chessBoardBase, Side.White);
    Pawn pawn = new Pawn(chessBoardBase, Side.White);

    Location kingLocation = new Location(3, 2);
    chessBoardBase.setPiece(king, kingLocation);
    chessBoardBase.setPiece(rook1, new Location(2, 3));
    chessBoardBase.setPiece(rook2, new Location(3, 4));
    chessBoardBase.setPiece(pawn, new Location(5, 2));

    Set<Move> moves = chessBoardBase.getMoveHints(kingLocation);
    assertEquals(2, moves.size());
    Move move = new Move(kingLocation, new Location(4, 2));
    assertTrue(moves.contains(move));
    move = new Move(kingLocation, new Location(2, 3));
    assertFalse(moves.contains(move));
    move.setIsAttack(true);
    assertTrue(moves.contains(move));
  }

}