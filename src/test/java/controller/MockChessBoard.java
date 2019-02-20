package controller;

import javax.swing.Icon;
import utils.Location;
import view.ChessBoardCallBack;
import view.ChessBoardInterface;

public class MockChessBoard implements ChessBoardInterface {

  private int mPieceCounter;
  private int mHintColorCounter;
  private int mWarningColorCounter;
  private int mRows;
  private int mCols;

  MockChessBoard(int height, int width) {
    mPieceCounter = 0;
    mHintColorCounter = 0;
    mWarningColorCounter = 0;
    mRows = height;
    mCols = width;
  }

  @Override
  public void setCallBack(ChessBoardCallBack callBack) {
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

  @Override
  public int getGridRows() {
    return mRows;
  }

  @Override
  public int getGridCols() {
    return mCols;
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
