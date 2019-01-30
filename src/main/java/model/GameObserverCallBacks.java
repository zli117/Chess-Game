package model;

import utils.Location;

public interface GameObserverCallBacks {

  void lost(Side loser);

  void pieceMoved(ChessBoardBase chessBoard, Location from, Location to);

  void pieceKilled(ChessBoardBase chessBoard, Piece pieceKilled);

}
