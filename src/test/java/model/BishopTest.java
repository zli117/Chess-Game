package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Set;
import javax.imageio.ImageIO;
import org.junit.Test;
import utils.Location;
import utils.Move;

public class BishopTest {

  @Test
  public void testGetPossibleMoves() {
    ChessBoardBase chessBoardBase = new ChessBoardBase(5, 5);
    Bishop bishop = new Bishop(chessBoardBase, Side.Black);
    MockPiece mockPiece1 = new MockPiece(chessBoardBase, Side.Black);
    MockPiece mockPiece2 = new MockPiece(chessBoardBase, Side.White);

    Location bishopLocation = new Location(2, 2);
    Location mock1Location = new Location(0, 0);
    Location mock2Location = new Location(0, 4);
    chessBoardBase.setPiece(bishop, bishopLocation);
    chessBoardBase.setPiece(mockPiece1, mock1Location);
    chessBoardBase.setPiece(mockPiece2, mock2Location);

    Set<Move> possibleMoves = bishop.getMovesAndAttacks();
    assertEquals(20, possibleMoves.size());
    Move move = new Move(bishopLocation, mock1Location);
    assertTrue(possibleMoves.contains(move));
    move.attack();
    assertTrue(possibleMoves.contains(move));
    move = new Move(bishopLocation, new Location(5, 5));
    assertTrue(possibleMoves.contains(move));
    move.attack();
    assertTrue(possibleMoves.contains(move));
    move = new Move(bishopLocation, new Location(6, 6));
    assertFalse(possibleMoves.contains(move));
    move.attack();
    assertFalse(possibleMoves.contains(move));
  }

  @Test
  public void testImageUrlIsValid() {
    try {
      Bishop bishop = new Bishop(null, Side.White);
      URL imageURL = bishop.getImageResourceUrl();
      BufferedImage image = ImageIO.read(imageURL);
    } catch (Exception exception) {
      fail();
    }
    try {
      Bishop bishop = new Bishop(null, Side.Black);
      URL imageURL = bishop.getImageResourceUrl();
      BufferedImage image = ImageIO.read(imageURL);
    } catch (Exception exception) {
      fail();
    }
  }

}