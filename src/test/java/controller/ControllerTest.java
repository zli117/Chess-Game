package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import model.ChessBoardBase;
import utils.Side;
import org.junit.Test;
import utils.BoardBuilder;
import utils.Location;

public class ControllerTest {

  @Test
  public void testRedrawBoard() {
//    try {
//      ChessBoardBase chessBoardBase = BoardBuilder
//          .constructFromFile(getClass().getResource("/board.conf"));
//      MockChessBoard mockChessBoard = new MockChessBoard(
//          chessBoardBase.getHeight(), chessBoardBase.getWidth());
//      Controller controller = new Controller(chessBoardBase, mockChessBoard);
//      controller.boardRedraw();
//      assertEquals(32, mockChessBoard.getPieceCounter());
//      chessBoardBase
//          .setPiece(new MockPieceWithInvalidUrl(chessBoardBase, Side.Black),
//              new Location(4, 4));
//      mockChessBoard.resetPieceCounter();
//      controller.boardRedraw();
//      // The mock piece should not show up since the url for icon is invalid.
//      assertEquals(32, mockChessBoard.getPieceCounter());
//    } catch (IOException exception) {
//      fail();
//    }
//    try {
//      BufferedImage image = ImageIO.read(getClass().getResource("/magic.txt"));
//      ImageIcon imageIcon = new ImageIcon(image);
//      fail();
//    } catch (Exception e) {
//      System.out.println(e);
//    }
  }

  @Test
  public void testGridClicked() {
//    try {
//      // Stalemate condition
//      ChessBoardBase chessBoardBase = BoardBuilder
//          .constructFromFile(getClass().getResource("/board_stalemate.conf"));
//      MockChessBoard mockChessBoard = new MockChessBoard(
//          chessBoardBase.getHeight(), chessBoardBase.getWidth());
//      Controller controller = new Controller(chessBoardBase, mockChessBoard);
//      mockChessBoard.resetHintColorCounter();
//      controller.gridClicked(new Location(3, 6));
//      assertEquals(23, mockChessBoard.getHintColorCounter());
//      controller.gridClicked(new Location(2, 6));
//      assertTrue(mockChessBoard.isFrozen());
//    } catch (IOException e) {
//      fail();
//    }
//
//    try {
//      // Checkmate condition
//      ChessBoardBase chessBoardBase = BoardBuilder
//          .constructFromFile(getClass().getResource("/board_checkmate.conf"));
//      MockChessBoard mockChessBoard = new MockChessBoard(
//          chessBoardBase.getHeight(), chessBoardBase.getWidth());
//      Controller controller = new Controller(chessBoardBase, mockChessBoard);
//      controller.gridClicked(new Location(7, 6));
//      assertEquals(14, mockChessBoard.getHintColorCounter());
//      controller.gridClicked(new Location(7, 7));
//      assertTrue(mockChessBoard.isFrozen());
//      assertEquals(1, mockChessBoard.getWarningColorCounter());
//    } catch (IOException e) {
//      fail();
//    }
  }

}