package model;

import utils.Location;
import utils.Move;

public class MockCallBack implements GameObserverCallBacks {

  private Side mCheckedSide;
  private Side mStaledSide;
  private Move mMove;
  private Piece mRemoved;

  /**
   * Mock a callback.
   */
  MockCallBack() {
    mCheckedSide = null;
    mStaledSide = null;
    mMove = null;
    mRemoved = null;
  }

  @Override
  public void pieceMoved(Move move) {
    mMove = move;
  }

  @Override
  public void pieceRemoved(Piece pieceRemoved, Location location) {
    mRemoved = pieceRemoved;
  }

  public Side getCheckedSide() {
    return mCheckedSide;
  }

  public Side getStaledSide() {
    return mStaledSide;
  }

  public Move getMove() {
    return mMove;
  }

  public Piece getRemoved() {
    return mRemoved;
  }

}
