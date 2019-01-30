package model;

import utils.Location;

public interface GameObserverCallBacks {

  void onCheckmate(Side loser);

  void onStalemate(Side loser);

  void pieceMoved(ChessBoardBase chessBoard, Location from, Location to);

  void pieceKilled(ChessBoardBase chessBoard, Piece pieceKilled);

}
