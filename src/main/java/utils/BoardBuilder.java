package utils;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Bishop;
import model.ChessBoardBase;
import model.Jumper;
import model.King;
import model.Knight;
import model.Pawn;
import model.Piece;
import model.Queen;
import model.Rook;
import model.Side;
import model.StandardChessBoard;
import model.SuperQueen;

public class BoardBuilder {

  /**
   * Create a board from board class name.
   *
   * @param boardName The class name of the board.
   * @param height    The height of the board.
   * @param width     The width of the board.
   */
  public static ChessBoardBase boardFactory(String boardName, int height,
      int width) {
    if (boardName.equals("StandardChessBoard")) {
      return new StandardChessBoard(height, width);
    }
    return null;
  }

  /**
   * Create a piece from piece name and side name.
   */
  public static Piece pieceFactory(ChessBoardBase board, String pieceName,
      String sideName) {
    Side side;
    try {
      side = Side.valueOf(sideName);
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Unknown side name: " + sideName);
    }
    if (pieceName.equals("Bishop")) {
      return new Bishop(board, side);
    }
    if (pieceName.equals("SuperQueen")) {
      return new SuperQueen(board, side);
    }
    if (pieceName.equals("Jumper")) {
      return new Jumper(board, side);
    }
    if (pieceName.equals("King")) {
      return new King(board, side);
    }
    if (pieceName.equals("Knight")) {
      return new Knight(board, side);
    }
    if (pieceName.equals("Pawn")) {
      Pawn pawn = new Pawn(board, side);
      pawn.setMovingUp(side == Side.White);
      return pawn;
    }
    if (pieceName.equals("Rook")) {
      return new Rook(board, side);
    }
    if (pieceName.equals("Queen")) {
      return new Queen(board, side);
    }
    return null;
  }

  /**
   * Construct a board from a file.
   *
   * @param filePath The file URL.
   */
  public static ChessBoardBase constructFromFile(URL filePath)
      throws RuntimeException, IOException {
    Scanner scanner = new Scanner(filePath.openStream());
    int height = Integer.parseInt(scanner.nextLine());
    int width = Integer.parseInt(scanner.nextLine());
    String boardName = scanner.nextLine();
    ChessBoardBase board = boardFactory(boardName, height, width);
    if (board == null) {
      throw new RuntimeException("Invalid board name: " + boardName);
    }
    Pattern pattern = Pattern.compile(
        "\\s*((?<piece>[a-zA-Z]+)_(?<side>[A-Z][a-zA-Z]*))?(\\s*|,)");
    for (int i = 0; i < height; ++i) {
      String row = scanner.nextLine();
      Matcher matcher = pattern.matcher(row);
      int j = 0;
      for (int k = 0; k < row.length() && j < width && matcher.find(k);
          ++j, k = matcher.end() + 1) {
        String pieceName = matcher.group("piece");
        String sideName = matcher.group("side");
        if (pieceName == null && sideName == null) {
          continue;
        }
        Piece piece = pieceFactory(board, pieceName, sideName);
        if (piece == null) {
          throw new RuntimeException(
              String.format("Invalid piece config at %d, %d: (%s, %s)", i, j,
                  pieceName, sideName));
        }
        if (piece instanceof King) {
          board.setKing((King) piece, new Location(i, j));
        } else {
          board.setPiece(piece, new Location(i, j));
        }
      }
      if (j != width) {
        throw new RuntimeException(
            String.format("Insufficient pieces for row %d, expected %d, got %d",
                i, width, j));
      }
    }
    return board;
  }

}
