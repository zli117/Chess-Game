package view;

import javax.swing.Icon;
import utils.Location;

public interface ChessBoardInterface {

  /**
   * Add a callback.
   */
  void setCallBack(ChessBoardCallBack callBack);

  /**
   * Set icon at location.
   */
  void setIcon(Location location, Icon icon);

  /**
   * Show selected color at location.
   */
  void showSelectedColor(Location location);

  /**
   * Show the hint color at location.
   */
  void showHintColor(Location location);

  /**
   * Show the warning color at location.
   */
  void showWarningColor(Location location);

  /**
   * Reset all grids to default color.
   */
  void resetAllColor();

  /**
   * Get number of rows in this board.
   */
  int getGridRows();

  /**
   * Get number of columns in this board.
   */
  int getGridCols();

}
