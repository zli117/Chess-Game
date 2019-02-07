package Controller;

import java.io.FileNotFoundException;
import model.ChessBoardBase;
import model.StandardChessBoard;
import utils.BoardBuilder;
import view.ChessBoard;
import view.Window;

public class Main {

  public static void main(String[] args) {
    String filePath = args[0];
    ChessBoardBase chessBoard = null;
    try {
      chessBoard = BoardBuilder
          .constructFromFile(Main.class.getResource(filePath));
    } catch (FileNotFoundException missingFile) {
      System.err.println(missingFile);
      System.exit(1);
    }

    ChessBoard chessBoardView = new ChessBoard(chessBoard.getHeight(),
        chessBoard.getWidth());
    Controller controller = new Controller((StandardChessBoard) chessBoard,
        chessBoardView);
    controller.boardRedraw();
    Window window = new Window("Chess Game!!", chessBoardView);
    window.setVisible(true);
  }

}
