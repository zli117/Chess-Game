package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JPanel;
import utils.Location;

public class ChessBoard extends JPanel {

  private final int GRID_SIZE = 70;
  private ChessBoardGrid[][] mChessSquares;
  private ChessBoardCallBack mCallBack;
  private boolean mFrozen;
  private int mHeight;
  private int mWidth;

  /**
   * Create a chess board widget with height and width.
   */
  public ChessBoard(int height, int width) {
    super();
    mHeight = height;
    mWidth = width;
    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    mChessSquares = new ChessBoardGrid[height][width];
    for (int i = 0; i < height; ++i) {
      for (int j = 0; j < width; ++j) {
        mChessSquares[i][j] = getGridButton(i, j);
        constraints.gridx = j;
        constraints.gridy = i;
        add(mChessSquares[i][j], constraints);
      }
      mCallBack = null;
      mFrozen = false;
    }
  }

  /**
   * Add a callback.
   */
  public void setCallBack(ChessBoardCallBack callBack) {
    mCallBack = callBack;
  }

  /**
   * Set a button at location.
   */
  private ChessBoardGrid getGridButton(int row, int col) {
    final Color[] colors = {new Color(254, 205, 159), new Color(211, 140, 71)};
    final Color hintColor = new Color(255, 124, 253, 255);
    final Color selectedColor = new Color(151, 255, 248, 255);
    final Color warningColor = new Color(20, 22, 255, 255);
    final ChessBoardGrid button = new ChessBoardGrid();
    Location location = new Location(row, col);
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (!mFrozen && mCallBack != null) {
          mCallBack.gridClicked(location);
        }
      }
    });
    button.setBackgroundColor(colors[(row + col) % 2]);
    button.setHintColor(hintColor);
    button.setSelectedColor(selectedColor);
    button.setWarningColor(warningColor);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.resetColor();
    button.setMinimumSize(new Dimension(GRID_SIZE, GRID_SIZE));
    button.setPreferredSize(new Dimension(GRID_SIZE, GRID_SIZE));
    button.setMaximumSize(new Dimension(GRID_SIZE, GRID_SIZE));
    return button;
  }

  /**
   * get a grid at location.
   */
  private ChessBoardGrid getGrid(Location location) {
    return mChessSquares[location.getRow()][location.getCol()];
  }

  /**
   * Set icon at location.
   */
  public void setIcon(Location location, Icon icon) {
    ChessBoardGrid grid = getGrid(location);
    if (grid != null) {
      grid.setIcon(icon);
    }
  }

  /**
   * Show selected color at location.
   */
  public void showSelectedColor(Location location) {
    ChessBoardGrid grid = getGrid(location);
    if (grid != null) {
      grid.showSelected();
    }
  }

  /**
   * Show the hint color at location.
   */
  public void showHintColor(Location location) {
    ChessBoardGrid grid = getGrid(location);
    if (grid != null) {
      grid.showHint();
    }
  }

  /**
   * Show the warning color at location.
   */
  public void showWarningColor(Location location) {
    ChessBoardGrid grid = getGrid(location);
    if (grid != null) {
      grid.showWarning();
    }
  }

  /**
   * Reset all grids to default color.
   */
  public void resetAllColor() {
    for (ChessBoardGrid[] row : mChessSquares) {
      for (ChessBoardGrid grid : row) {
        grid.resetColor();
      }
    }
  }

  /**
   * Unfreeze the board.
   */
  public void unFreeze() {
    mFrozen = false;
  }

  public int getGridRows() {
    return mHeight;
  }

  public int getGridCols() {
    return mWidth;
  }

}
