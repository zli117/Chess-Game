package controller;

import javax.swing.UIManager;
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

    ChessBoard chessBoardView = new ChessBoard(8, 8);
    Window window = new Window("Chess Game!!", chessBoardView);
    window.setVisible(true);
    window.pack();
    Controller controller = new Controller(window);
    if (!controller.loadConfig(Main.class.getResource(filePath))) {
      System.err.printf(
          "%s is not a valid resource path. Falling back to default board\n",
          filePath);
      controller.loadConfig(Main.class.getResource("/board.conf"));
    }
  }

}
