package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import utils.Location;
import utils.Side;

public class ControllerTest {

  @Test
  public void testLoadConfig() {
    MockChessBoard mockChessBoard = new MockChessBoard(8, 8);
    MockWindow mockWindow = new MockWindow(mockChessBoard);
    Controller controller = new Controller(mockWindow);
    assertTrue(controller.loadConfig(getClass().getResource("/board.conf")));
    assertFalse(
        controller.loadConfig(getClass().getResource("/invalid_board_1.conf")));

    mockChessBoard = new MockChessBoard(9, 8);
    mockWindow = new MockWindow(mockChessBoard);
    controller = new Controller(mockWindow);
    assertFalse(controller.loadConfig(getClass().getResource("/board.conf")));
  }

  @Test
  public void testForfeit() {
    MockChessBoard mockChessBoard = new MockChessBoard(8, 8);
    MockWindow mockWindow = new MockWindow(mockChessBoard);
    Controller controller = new Controller(mockWindow);
    controller.onForfeit();
    assertEquals(1, controller.getScore(Side.Black));
    assertEquals(0, controller.getScore(Side.White));
  }

  @Test
  public void testGridClicked() {
    // Stalemate condition
    MockChessBoard mockChessBoard = new MockChessBoard(8, 8);
    MockWindow mockWindow = new MockWindow(mockChessBoard);
    Controller controller = new Controller(mockWindow);
    assertTrue(
        controller.loadConfig(getClass().getResource("/board_stalemate.conf")));
    mockChessBoard.resetHintColorCounter();
    controller.gridClicked(new Location(3, 6));
    assertEquals(23, mockChessBoard.getHintColorCounter());
    controller.gridClicked(new Location(2, 6));

    // Checkmate condition
    mockChessBoard = new MockChessBoard(8, 8);
    mockWindow = new MockWindow(mockChessBoard);
    controller = new Controller(mockWindow);
    assertTrue(
        controller.loadConfig(getClass().getResource("/board_checkmate.conf")));
    controller.gridClicked(new Location(7, 6));
    assertEquals(14, mockChessBoard.getHintColorCounter());
    controller.gridClicked(new Location(7, 7));
    assertEquals(1, mockChessBoard.getWarningColorCounter());
  }

  @Test
  public void testOnOpenConfig() {
    MockChessBoard mockChessBoard = new MockChessBoard(8, 8);
    MockWindow mockWindow = new MockWindow(mockChessBoard);
    Controller controller = new Controller(mockWindow);

    controller.onOpenConfig(getClass().getResource("/board.conf"));
    assertFalse(mockWindow.mShowErrorDialogCalled);
    assertFalse(mockWindow.mUndoEnabled);

    controller.gridClicked(new Location(6, 2));
    controller.gridClicked(new Location(4, 2));

    assertTrue(mockWindow.mUndoEnabled);
    controller.onOpenConfig(getClass().getResource("/invalid_baord_1.conf"));
    assertTrue(mockWindow.mUndoEnabled);
    assertTrue(mockWindow.mShowErrorDialogCalled);

    mockWindow.mShowErrorDialogCalled = false;
    controller.onOpenConfig(getClass().getResource("/board.conf"));
    assertFalse(mockWindow.mShowErrorDialogCalled);
    assertFalse(mockWindow.mUndoEnabled);
  }

  @Test
  public void testOnRestart() {
    MockChessBoard mockChessBoard = new MockChessBoard(8, 8);
    MockWindow mockWindow = new MockWindow(mockChessBoard);
    Controller controller = new Controller(mockWindow);
    controller.loadConfig(getClass().getResource("/board.conf"));

    controller.gridClicked(new Location(6, 2));
    controller.gridClicked(new Location(4, 2));

    assertTrue(mockWindow.mUndoEnabled);

    controller.onRestart(false);

    assertFalse(mockWindow.mUndoEnabled);
    assertEquals(0, mockWindow.mScores[0]);
    assertEquals(0, mockWindow.mScores[1]);

    controller.gridClicked(new Location(6, 2));
    controller.gridClicked(new Location(4, 2));
    assertTrue(mockWindow.mUndoEnabled);

    controller.onRestart(true);

    assertFalse(mockWindow.mUndoEnabled);
    assertEquals(1, mockWindow.mScores[0]);
    assertEquals(1, mockWindow.mScores[1]);
  }

  @Test
  public void testUndo() {
    MockChessBoard mockChessBoard = new MockChessBoard(8, 8);
    MockWindow mockWindow = new MockWindow(mockChessBoard);
    Controller controller = new Controller(mockWindow);
    controller.loadConfig(getClass().getResource("/board.conf"));

    controller.gridClicked(new Location(6, 2));
    controller.gridClicked(new Location(4, 2));

    controller.gridClicked(new Location(0, 0));
    controller.gridClicked(new Location(1, 0));

    controller.gridClicked(new Location(4, 2));
    controller.gridClicked(new Location(3, 2));

    assertTrue(mockWindow.mUndoEnabled);

    controller.onUndo();
    controller.onUndo();
    controller.onUndo();

    assertFalse(mockWindow.mUndoEnabled);

    mockChessBoard.resetHintColorCounter();
    controller.gridClicked(new Location(6, 2));

    assertEquals(2, mockChessBoard.getHintColorCounter());

    // Test undo in castling
    mockChessBoard = new MockChessBoard(8, 8);
    mockWindow = new MockWindow(mockChessBoard);
    controller = new Controller(mockWindow);
    controller.loadConfig(getClass().getResource("/board_castling.conf"));

    controller.gridClicked(new Location(7, 4));
    controller.gridClicked(new Location(7, 2));
    controller.gridClicked(new Location(0, 4));
    controller.gridClicked(new Location(0, 3));

    assertTrue(mockWindow.mUndoEnabled);
    // Since if castling is successful, the black shouldn't be able to move
    // to 0, 3
    assertEquals(Side.Black, mockWindow.mCurrentSide);

    controller.onUndo();
    mockChessBoard.resetHintColorCounter();
    controller.gridClicked(new Location(7, 0));
    assertEquals(10, mockChessBoard.getHintColorCounter());
    controller.gridClicked(new Location(0, 2));

    mockChessBoard.resetHintColorCounter();
    controller.gridClicked(new Location(7, 4));
    assertEquals(7, mockChessBoard.getHintColorCounter());
  }

}