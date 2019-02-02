package model;

import static org.junit.Assert.*;

import java.util.Set;
import org.junit.Test;
import utils.Location;
import utils.Move;

public class KnightTest {

  @Test
  public void testPossibleMoves() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(8, 8);
    Knight knight = new Knight(chessBoardBase, Side.Black);
    MockPiece mockPiece1 = new MockPiece(chessBoardBase, Side.Black);
    MockPiece mockPiece2 = new MockPiece(chessBoardBase, Side.White);

    Location knightLocation = new Location(4, 4);
    Location mock1Location = new Location(2, 3);
    Location mock2Location = new Location(2, 5);

    chessBoardBase.setPiece(knight, knightLocation);
    chessBoardBase.setPiece(mockPiece1, mock1Location);
    chessBoardBase.setPiece(mockPiece2, mock2Location);

    Set<Move> moves = knight.getMovesAndAttacks();

    assertEquals(16, moves.size());
    Move move = new Move(knightLocation, mock1Location);
    assertTrue(moves.contains(move));
    move.setIsAttack(true);
    assertTrue(moves.contains(move));
  }

}