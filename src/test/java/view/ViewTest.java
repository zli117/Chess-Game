package view;

import controller.Controller;
import java.io.FileNotFoundException;
import model.ChessBoardBase;
import org.junit.Test;
import utils.BoardBuilder;

public class ViewTest {

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
    String testInstructions = "Nothing here";
    testDifferentBoardConfig("/board.conf", testInstructions);
  }

  @Test
  public void testSuperQueen() {
    String testInstructions = "Nothing here";
    testDifferentBoardConfig("/board_with_squeen.conf", testInstructions);
  }

  @Test
  public void testJumper() {
    String testInstructions = "Nothing here";
    testDifferentBoardConfig("/board_with_jumper.conf", testInstructions);
  }

  @Test
  public void testStandardBoardWithMove() {
    String testInstructions = "Nothing here";
    testDifferentBoardConfigWithMove("/board.conf", testInstructions);
  }

  @Test
  public void testSuperQueenWithMove() {
    String testInstructions = "Nothing here";
    testDifferentBoardConfigWithMove("/board_with_squeen.conf",
        testInstructions);
  }

  @Test
  public void testJumperWithMove() {
    String testInstructions = "Nothing here";
    testDifferentBoardConfigWithMove("/board_with_jumper.conf",
        testInstructions);
  }

}
