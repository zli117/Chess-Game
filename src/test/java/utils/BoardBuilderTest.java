package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import model.ChessBoardBase;
import model.Jumper;
import model.Piece;
import model.Queen;
import model.Side;
import model.StandardChessBoard;
import model.SuperQueen;
import org.junit.Test;

public class BoardBuilderTest {

  @Test
  public void testLoadFromFile() {
    try {
      ChessBoardBase chessBoardBase = BoardBuilder
          .constructFromFile(getClass().getResource("/board_stalemate.conf"));
      assertNotNull(chessBoardBase);
      assertTrue(chessBoardBase instanceof StandardChessBoard);
      assertEquals(8, chessBoardBase.getWidth());
      assertEquals(8, chessBoardBase.getHeight());
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testCreateBoard() {
    ChessBoardBase chessBoard = BoardBuilder
        .boardFactory("StandardChessBoard", 8, 7);
    assertTrue(chessBoard instanceof StandardChessBoard);
    assertEquals(8, chessBoard.getHeight());
    assertEquals(7, chessBoard.getWidth());

    chessBoard = BoardBuilder.boardFactory("StandardChessBoard ", 8, 7);
    assertNull(chessBoard);
  }

  @Test
  public void testCreatePiece() {
    ChessBoardBase chessBoard = new ChessBoardBase(8, 3);
    Piece piece = BoardBuilder.pieceFactory(chessBoard, "Queen", "White");
    assertTrue(piece instanceof Queen);
    assertEquals(Side.White, piece.getSide());
    piece = BoardBuilder.pieceFactory(chessBoard, "SuperQueen", "White");
    assertTrue(piece instanceof SuperQueen);
    piece = BoardBuilder.pieceFactory(chessBoard, "Jumper", "White");
    assertTrue(piece instanceof Jumper);
    BoardBuilder.pieceFactory(chessBoard, "Pawn", "Black");

    try {
      BoardBuilder.pieceFactory(chessBoard, "Bishop", "White_-");
      fail();
    } catch (RuntimeException exception) {
      // Pass
    }

    assertNull(BoardBuilder.pieceFactory(chessBoard, "Quee", "White"));
  }

  @Test
  public void testInvalidBoard1() {
    try {
      ChessBoardBase chessBoard = BoardBuilder
          .constructFromFile(getClass().getResource("/invalid_board_1.conf"));
      fail();
    } catch (IOException e) {
      fail();
    } catch (RuntimeException runtimeException) {
      assertEquals("Invalid board name: StandardChess",
          runtimeException.getMessage());
    }
  }

  @Test
  public void testInvalidBoard2() {
    try {
      ChessBoardBase chessBoard = BoardBuilder
          .constructFromFile(getClass().getResource("/invalid_board_2.conf"));
      System.out.println(chessBoard);
      fail();
    } catch (IOException e) {
      fail();
    } catch (RuntimeException runtimeException) {
      assertEquals("Invalid piece config at 0, 2: (Bshop, Black)",
          runtimeException.getMessage());
    }
  }

  @Test
  public void testInvalidBoard3() {
    try {
      ChessBoardBase chessBoard = BoardBuilder
          .constructFromFile(getClass().getResource("/invalid_board_3.conf"));
      System.out.println(chessBoard);
      fail();
    } catch (IOException e) {
      fail();
    } catch (RuntimeException runtimeException) {
      assertEquals("Insufficient pieces for row 1, expected 8, got 7",
          runtimeException.getMessage());
    }
  }

}