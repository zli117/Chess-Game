package model;

import utils.Location;
import utils.Move;

public interface GameObserverCallBacks {

  void pieceMoved(Move move);

  void pieceRemoved(Piece pieceRemoved, Location originalLocation);

}
