package controller;

import utils.Side;
import view.ChessBoardInterface;
import view.WindowCallBack;
import view.WindowInterface;

public class MockWindow implements WindowInterface {

  boolean mUndoEnabled;
  Side mCurrentSide;
  int[] mScores;
  Side mCheckedSide;
  boolean mShowStalemateCalled;
  boolean mShowErrorDialogCalled;
  private ChessBoardInterface mChessboard;

  MockWindow(ChessBoardInterface chessBoard) {
    mChessboard = chessBoard;
    mScores = new int[2];
    mCheckedSide = null;
    mShowStalemateCalled = false;
    mShowErrorDialogCalled = false;
  }

  @Override
  public void setCallBack(WindowCallBack callBack) {
  }

  @Override
  public ChessBoardInterface getChessBoard() {
    return mChessboard;
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
