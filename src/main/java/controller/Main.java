package controller;

import java.io.IOException;
import javax.swing.UIManager;
import model.ChessBoardBase;
import utils.BoardBuilder;
import view.ChessBoard;
import view.Window;

public class Main {

  /**
   * Main method.
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      // Do nothing.
    }
    String filePath = args[0];
    ChessBoardBase chessBoard = null;
    try {
      chessBoard = BoardBuilder
          .constructFromFile(Main.class.getResource(filePath));
    } catch (IOException exception) {
      System.err.println(exception);
      System.exit(1);
    }

    ChessBoard chessBoardView = new ChessBoard(chessBoard.getHeight(),
        chessBoard.getWidth());
    Controller controller = new Controller(chessBoard, chessBoardView);
    controller.boardRedraw();
    Window window = new Window("Chess Game!!", chessBoardView, controller);
    window.setVisible(true);
  }

}
