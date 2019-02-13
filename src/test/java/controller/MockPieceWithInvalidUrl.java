package controller;

import java.net.URL;
import model.ChessBoardBase;
import model.MockPiece;
import model.Side;

public class MockPieceWithInvalidUrl extends MockPiece {

  MockPieceWithInvalidUrl(ChessBoardBase chessBoard,
      Side side) {
    super(chessBoard, side);
  }

  @Override
  public URL getImageResourceUrl() {
    return getClass().getResource("/");
  }

}
