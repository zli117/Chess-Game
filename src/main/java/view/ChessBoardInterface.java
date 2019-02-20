package view;

import javax.swing.Icon;
import javax.swing.JPanel;
import utils.Location;

public abstract class ChessBoardInterface extends JPanel {

  /**
   * Add a callback.
   */
  public abstract void setCallBack(ChessBoardCallBack callBack);

  /**
   * Set icon at location.
   */
  public abstract void setIcon(Location location, Icon icon);

  /**
   * Show selected color at location.
   */
  public abstract void showSelectedColor(Location location);

  /**
   * Show the hint color at location.
   */
  public abstract void showHintColor(Location location);

  /**
   * Show the warning color at location.
   */
  public abstract void showWarningColor(Location location);

  /**
   * Reset all grids to default color.
   */
  public abstract void resetAllColor();

  /**
   * Get number of rows in this board.
   */
  public abstract int getGridRows();

  /**
   * Get number of columns in this board.
   */
  public abstract int getGridCols();

}
