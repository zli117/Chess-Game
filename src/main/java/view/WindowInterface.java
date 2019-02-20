package view;

import utils.Side;

public interface WindowInterface {

  /**
   * Set whether undo menu item is enabled.
   */
  void setEnabledUndoButton(boolean enabled);

  /**
   * Set the window callback.
   */
  void setCallBack(WindowCallBack callBack);

  /**
   * Set the current side to play.
   */
  void setCurrentSide(Side side);

  /**
   * Set the score of a side.
   */
  void setScore(Side side, int score);

  /**
   * Get the chess board in this window.
   */
  ChessBoardInterface getChessBoard();

  /**
   * Show a dialog to inform player of checkmate of a side.
   */
  void showCheckmate(Side lost);

  /**
   * Show a dialog to inform player of stalemate.
   */
  void showStalemate();

  /**
   * Show a dialog to inform player of some error.
   */
  void showErrorDialog(String reason);

}
