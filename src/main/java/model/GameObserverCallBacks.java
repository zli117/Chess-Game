package model;

import utils.Move;

public interface GameObserverCallBacks {

  void onCheckmate(Side loser);

  void onStalemate(Side loser);

  void pieceMoved(ChessBoardBase chessBoard, Move move);

  void pieceRemoved(ChessBoardBase chessBoard, Piece pieceKilled);

}
