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
    String testName = Thread.currentThread().getStackTrace()[2].getMethodName();
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
            env, testName);
        testWindow.setVisible(true);
      }
    };
  }

  /**
   * Helper function to test movable board.
   */
  public void testDifferentBoardConfigWithMove(String configPath,
      String instructions) {
    String testName = Thread.currentThread().getStackTrace()[2].getMethodName();
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
            instructions, env, testName);
        testWindow.setVisible(true);
      }
    };
  }

  @Test
  public void testStandardBoard() {
    String testInstructions = "Click at all the white pawns, and verify they "
        + "can move two steps. Then click at both white knights and verify "
        + "there are two moves available for each. Verify the display of the "
        + "board is consistent with the standard chess board as shown below.";
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
    String testInstructions = "Verify there are two jumpers on the board. One "
        + "from white and another from black. Click on the white jumper, verify "
        + "the move pattern corresponds to the screen shot.";
    testDifferentBoardConfig("/board_with_jumper.conf", testInstructions);
  }

  @Test
  public void testStandardBoardWithMove() {
    String testInstructions = "Check whether the board is consistent with "
        + "standard chess board. Click \"Enable move\". Move one white piece. "
        + "Then verify that no white piece is allowed to move. Also verify if "
        + "the black piece has legal move, it's allowed to move.";
    testDifferentBoardConfigWithMove("/board.conf", testInstructions);
  }

  @Test
  public void testSuperQueenWithMove() {
    String testInstructions = "Click \"Enable move\". Move leftmost white pawn "
        + "up by two tiles. Then verify that no white piece is allowed to "
        + "move. Also verify if any black piece has legal move, it's allowed "
        + "to move.";
    testDifferentBoardConfigWithMove("/board_with_squeen.conf",
        testInstructions);
  }

  @Test
  public void testJumperWithMove() {
    String testInstructions = "Click on \"Enable move\". Then click on white "
        + "jumper and move it to the right by two tiles. Next move the "
        + "leftmost black pawn down by one tile. Then move the white jumper up "
        + "to capture the black Bishop. Verify the black Bishop has been "
        + "captured, and black King is not under check.";
    testDifferentBoardConfigWithMove("/board_with_jumper.conf",
        testInstructions);
  }

  @Test
  public void testUnderCheckWarning() {
    String testInstructions = "Click on \"Enable move\". Then move the white "
        + "rook up by one tile. Check the black king's grid turns blue as a "
        + "warning for checking. Verify chess board looks like the screen shot";
    testDifferentBoardConfigWithMove("/board_check_warning.conf",
        testInstructions);
  }

  @Test
  public void testStaleMate() {
    String testInstructions = "Click on \"Enable move\". The move the white "
        + "queen up by one row. Check terminal has printout of \"Stalemate\". "
        + "Click all three pieces on board, and verify that there's no move "
        + "recommendation from either side.";
    testDifferentBoardConfigWithMove("/board_stalemate.conf",
        testInstructions);
  }

  @Test
  public void testCheckMate() {
    String testInstructions = "Click on \"Enable move\". Then move the white "
        + "rook right by one tile. Check terminal has the printout of "
        + "\"Checkmate! Black lost\". Click all three pieces on board, and "
        + "verify that there's no move recommendation from either side and "
        + "black King is under check.";
    testDifferentBoardConfigWithMove("/board_checkmate.conf",
        testInstructions);
  }

}
