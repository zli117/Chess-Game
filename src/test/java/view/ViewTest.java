package view;

import static org.junit.Assert.fail;

import controller.Controller;
import java.io.IOException;
import javax.swing.JMenuBar;
import org.junit.Test;

public class ViewTest {


  /**
   * Helper function to test movable board.
   */
  private void testDifferentBoardConfigWithMove(String configPath,
      String instructions) {
    String testName = Thread.currentThread().getStackTrace()[2].getMethodName();
    new ManualTestEnv() {
      @Override
      void runTest(ManualTestEnv env) {
        ChessBoard chessBoardView = new ChessBoard(8, 8);
        Window window = new Window("Chess Game!!", chessBoardView);
        TestWindow testWindow = new TestWindow(window.getContentPane(),
            instructions, env, testName);
        testWindow.setJMenuBar(window.getJMenuBar());
        testWindow.setVisible(true);
        Controller controller = new Controller(window);
        if (!controller.loadConfig(getClass().getResource(configPath))) {
          fail();
        }
      }
    };
  }

  @Test
  public void testStandardBoardWithMove() {
    String testInstructions = "Click \"Enable move\". Move leftmost white pawn "
        + "up by two tiles. Then verify that no white piece is allowed to "
        + "move. Also verify if any black piece has legal move, it's allowed "
        + "to move.";
    testDifferentBoardConfigWithMove("/board.conf", testInstructions);
  }

  @Test
  public void testSuperQueenWithMove() {
    String testInstructions = "Click on \"Enable move\". Then click on white "
        + "super queen and move it all the way to the left. Then move the "
        + "leftmost black Pawn down by one tile. Then capture the Pawn below "
        + "black queen with the white SuperQueen. Verify black King is under "
        + "check (background turns blue)";
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
