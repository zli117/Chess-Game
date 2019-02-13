package view;

import controller.Controller;
import java.io.FileNotFoundException;
import model.ChessBoardBase;
import org.junit.Test;
import utils.BoardBuilder;

public class ViewTest {

  /**
   * Helper function to test static board.
   */
  public void testDifferentBoardConfig(String configPath,
      String testInstruction) {
    new ManualTestEnv() {
      @Override
      void runTest(ManualTestEnv env) {
        ChessBoardBase chessBoard = null;
        try {
          chessBoard = BoardBuilder
              .constructFromFile(getClass().getResource(configPath));
        } catch (FileNotFoundException missingFile) {
          System.err.println(missingFile);
          System.exit(1);
        }

        ChessBoard chessBoardView = new ChessBoard(chessBoard.getHeight(),
            chessBoard.getWidth());
        Controller controller = new Controller(chessBoard,
            chessBoardView);
        controller.boardRedraw();
        TestWindow testWindow = new TestWindow(chessBoardView, testInstruction,
            env);
        testWindow.setVisible(true);
      }
    };
  }

  /**
   * Helper function to test movable board.
   */
  public void testDifferentBoardConfigWithMove(String configPath,
      String instructions) {
    new ManualTestEnv() {
      @Override
      void runTest(ManualTestEnv env) {
        ChessBoardBase chessBoard = null;
        try {
          chessBoard = BoardBuilder
              .constructFromFile(getClass().getResource(configPath));
        } catch (FileNotFoundException missingFile) {
          System.err.println(missingFile);
          System.exit(1);
        }

        ChessBoard chessBoardView = new ChessBoard(chessBoard.getHeight(),
            chessBoard.getWidth());
        Controller controller = new Controller(chessBoard,
            chessBoardView);
        controller.boardRedraw();
        Window window = new Window("Chess Game!!", chessBoardView, controller);
        TestWindow testWindow = new TestWindow(window.getContentPane(),
            instructions, env);
        testWindow.setVisible(true);
      }
    };
  }

  @Test
  public void testStandardBoard() {
    String testInstructions = "Verify the display of the board is consistent "
        + "with the standard chess board. Click at the white pawns, verify "
        + "they can move two steps and verify white knights moves are correct.";
    testDifferentBoardConfig("/board.conf", testInstructions);
  }

  @Test
  public void testSuperQueen() {
    String testInstructions = "Verify there are two super queens on the board. "
        + "One from white and another from black. Click on the white super "
        + "queen, verify the move pattern corresponds to the screen shot.";
    testDifferentBoardConfig("/board_with_squeen.conf", testInstructions);
  }

  @Test
  public void testJumper() {
    String testInstructions = "Verify there are two jumpers on the board. "
        + "One from white and another from black. Click on the white jumper, "
        + "verify the move pattern corresponds to the screen shot.";
    testDifferentBoardConfig("/board_with_jumper.conf", testInstructions);
  }

  @Test
  public void testStandardBoardWithMove() {
    String testInstructions = "Check whether the board is consistent with "
        + "standard chess board. Click \"Enable move\".";
    testDifferentBoardConfigWithMove("/board.conf", testInstructions);
  }

  @Test
  public void testSuperQueenWithMove() {
    String testInstructions = "Click on \"Enable move\". Then click on white "
        + "super queen and move it to an empty location. Next move a black "
        + "piece, but make sure it don't capture the white super queen. Then "
        + "move the white super queen to capture one black piece.";
    testDifferentBoardConfigWithMove("/board_with_squeen.conf",
        testInstructions);
  }

  @Test
  public void testJumperWithMove() {
    String testInstructions = "Click on \"Enable move\". Then click on white "
        + "jumper and move it to an empty location. Next move a black "
        + "piece, but make sure it don't capture the white jumper. Then "
        + "move the white jumper to capture one black piece.";
    testDifferentBoardConfigWithMove("/board_with_jumper.conf",
        testInstructions);
  }

  @Test
  public void testUnderCheckWarning() {
    String testInstructions = "Click on \"Enable move\". The move the white "
        + "rook up by one row. Check the black king's grid turns blue as a "
        + "warning for checking.";
    testDifferentBoardConfigWithMove("/board_check_warning.conf",
        testInstructions);
  }

  @Test
  public void testStaleMate() {
    String testInstructions = "Click on \"Enable move\". The move the white "
        + "queen up by one row. Check terminal has printout of \"Stalemate\"."
        + " Click all three pieces on board, and verify that there's no move"
        + " recommendation from either side.";
    testDifferentBoardConfigWithMove("/board_stalemate.conf",
        testInstructions);
  }

  @Test
  public void testCheckMate() {
    String testInstructions = "Click on \"Enable move\". The move the white "
        + "rook right by one column. Check terminal has printout of "
        + "\"Checkmate! Black lost\". Click all three pieces on board, and "
        + "verify that there's no move recommendation from either side.";
    testDifferentBoardConfigWithMove("/board_checkmate.conf",
        testInstructions);
  }

}