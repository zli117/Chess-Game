package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JPanel;
import utils.Location;

public class ChessBoard extends JPanel {

  private ChessBoardGrid[][] mChessSquares;
  private ArrayList<ViewCallBack> mCallBacks;
  private boolean mFrozen;

  /**
   * Create a chess board widget with height and width.
   */
  public ChessBoard(int height, int width) {
    super();
    setLayout(new GridLayout(height, width));
    mChessSquares = new ChessBoardGrid[height][width];
    for (int i = 0; i < height; ++i) {
      for (int j = 0; j < width; ++j) {
        mChessSquares[i][j] = getGridButton(i, j);
        add(mChessSquares[i][j]);
      }
    }
    setMinimumSize(new Dimension(55 * width, 55 * height));
    setMaximumSize(new Dimension(55 * width, 55 * height));
    mCallBacks = new ArrayList<>();
    mFrozen = false;
  }

  /**
   * Add a callback.
   */
  public void addCallBack(ViewCallBack callBack) {
    mCallBacks.add(callBack);
  }

  /**
   * Set a button at location.
   */
  private ChessBoardGrid getGridButton(int row, int col) {
    final Color[] colors = {new Color(254, 205, 159), new Color(211, 140, 71)};
    final Color hintColor = new Color(255, 124, 253, 255);
    final Color selectedColor = new Color(151, 255, 248, 255);
    final Color warningColor = new Color(2, 4, 255, 255);
    final ChessBoardGrid button = new ChessBoardGrid();
    Location location = new Location(row, col);
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (!mFrozen) {
          for (ViewCallBack callBack : mCallBacks) {
            callBack.gridClicked(location);
          }
        }
      }
    });
    button.setDefaultColor(colors[(row + col) % 2]);
    button.setHintColor(hintColor);
    button.setSelectedColor(selectedColor);
    button.setWarningColor(warningColor);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.resetColor();
    button.setMinimumSize(new Dimension(55, 55));
    button.setMaximumSize(new Dimension(55, 55));
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
   * Freeze the board to prevent further moves.
   */
  public void freeze() {
    mFrozen = true;
  }

  /**
   * Unfreeze the board.
   */
  public void unFreeze() {
    mFrozen = false;
  }

}
