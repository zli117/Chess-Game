package model;

import java.util.ArrayList;
import java.util.List;
import utils.Location;
import utils.Move;
import utils.Pair;

public class MoveTracker implements GameObserverCallBacks {

  private List<Pair<Piece, Location>> mRemovedPieces;
  private List<Move> mMoves;

  public MoveTracker() {
    mRemovedPieces = new ArrayList<>();
    mMoves = new ArrayList<>();
  }


  @Override
  public void pieceMoved(Move move) {
    mMoves.add(move);
  }

  @Override
  public void pieceRemoved(Piece pieceRemoved, Location location) {
    mRemovedPieces.add(new Pair<>(pieceRemoved, location));
  }

  public List<Pair<Piece, Location>> getRemovedPieces() {
    return mRemovedPieces;
  }

  public List<Move> getMoves() {
    return mMoves;
  }

}
