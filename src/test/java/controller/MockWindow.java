package controller;

import utils.Side;
import view.ChessBoard;
import view.Window;

public class MockWindow extends Window {

  boolean mUndoEnabled;
  Side mCurrentSide;
  int[] mScores;
  Side mCheckedSide;
  boolean mShowStalemateCalled;
  boolean mShowErrorDialogCalled;

  MockWindow(ChessBoard chessBoard) {
    super("Mock", chessBoard);
    mScores = new int[2];
    mCheckedSide = null;
    mShowStalemateCalled = false;
    mShowErrorDialogCalled = false;
  }

  @Override
  public void setEnabledUndoButton(boolean enabled) {
    mUndoEnabled = enabled;
  }

  @Override
  public void setCurrentSide(Side side) {
    mCurrentSide = side;
  }

  @Override
  public void setScore(Side side, int score) {
    mScores[side.ordinal()] = score;
  }

  @Override
  public void showCheckmate(Side lost) {
    mCheckedSide = lost;
  }

  @Override
  public void showStalemate() {
    mShowStalemateCalled = true;
  }

  @Override
  public void showErrorDialog(String reason) {
    mShowErrorDialogCalled = true;
  }

}
