package controller;

import javax.swing.Icon;
import utils.Location;
import view.ChessBoard;
import view.ChessBoardCallBack;

public class MockChessBoard extends ChessBoard {

  private int mPieceCounter;
  private int mHintColorCounter;
  private int mWarningColorCounter;

  MockChessBoard(int height, int width) {
    super(height, width);
    mPieceCounter = 0;
    mHintColorCounter = 0;
    mWarningColorCounter = 0;
  }

  @Override
  public void setCallBack(ChessBoardCallBack callBack) {
    super.setCallBack(callBack);
  }

  @Override
  public void setIcon(Location location, Icon icon) {
    if (icon != null) {
      ++mPieceCounter;
    }
  }

  @Override
  public void showSelectedColor(Location location) {
  }

  @Override
  public void showHintColor(Location location) {
    ++mHintColorCounter;
  }

  @Override
  public void showWarningColor(Location location) {
    ++mWarningColorCounter;
  }

  @Override
  public void resetAllColor() {
  }

  int getPieceCounter() {
    return mPieceCounter;
  }

  void resetPieceCounter() {
    mPieceCounter = 0;
  }

  void resetHintColorCounter() {
    mHintColorCounter = 0;
  }

  int getHintColorCounter() {
    return mHintColorCounter;
  }

  int getWarningColorCounter() {
    return mWarningColorCounter;
  }

}
