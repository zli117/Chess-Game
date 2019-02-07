package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import utils.Location;

class ChessBoardGrid extends JButton {

  private int mRow;
  private int mCol;
  private Color mDefaultColor;
  private Color mSelectedColor;
  private Color mHintColor;
  private Color mWarningcolor;

  public ChessBoardGrid(int row, int col) {
    super();
    mRow = row;
    mCol = col;

    // To prevent showing a different color when clicked down on a button
    setContentAreaFilled(false);
    setOpaque(true);

    mDefaultColor = new Color(0, 0, 0);
    mSelectedColor = new Color(0, 0, 0);
    mHintColor = new Color(0, 0, 0);
    mWarningcolor = new Color(0, 0, 0);
  }

  public void setDefaultColor(Color defaultColor) {
    mDefaultColor = defaultColor;
  }

  public void setSelectedColor(Color selectedColor) {
    mSelectedColor = selectedColor;
  }

  public void setWarningColor(Color warningColor) {
    mWarningcolor = warningColor;
  }

  public void setHintColor(Color hintColor) {
    mHintColor = hintColor;
  }

  public int getRow() {
    return mRow;
  }

  public int getCol() {
    return mCol;
  }

  public void resetColor() {
    setBackground(mDefaultColor);
  }

  public void showSelected() {
    setBackground(mSelectedColor);
  }

  public void showWarning() {
    setBackground(mWarningcolor);
  }

  public void showHint() {
    setBackground(mHintColor);
  }

}

public class ChessBoard extends JPanel {

  private ChessBoardGrid[][] mChessSquares;
  private ArrayList<ViewCallBack> mCallBacks;
  private boolean mFrozen;

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

  public void addCallBack(ViewCallBack callBack) {
    mCallBacks.add(callBack);
  }

  private ChessBoardGrid getGridButton(int row, int col) {
    final Color[] colors = {new Color(254, 205, 159), new Color(211, 140, 71)};
    final Color hintColor = new Color(255, 124, 253, 255);
    final Color selectedColor = new Color(151, 255, 248, 255);
    final Color warningColor = new Color(255, 10, 7, 255);
    final ChessBoardGrid button = new ChessBoardGrid(row, col);
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

  private ChessBoardGrid getGrid(Location location) {
    return mChessSquares[location.getRow()][location.getCol()];
  }

  public void setIcon(Location location, Icon icon) {
    ChessBoardGrid grid = getGrid(location);
    if (grid != null) {
      grid.setIcon(icon);
    }
  }

  public void showSelectedColor(Location location) {
    ChessBoardGrid grid = getGrid(location);
    if (grid != null) {
      grid.showSelected();
    }
  }

  public void showHintColor(Location location) {
    ChessBoardGrid grid = getGrid(location);
    if (grid != null) {
      grid.showHint();
    }
  }

  public void showWarningColor(Location location) {
    ChessBoardGrid grid = getGrid(location);
    if (grid != null) {
      grid.showWarning();
    }
  }

  public void resetAllColor() {
    for (ChessBoardGrid[] row : mChessSquares) {
      for (ChessBoardGrid grid : row) {
        grid.resetColor();
      }
    }
  }

  public void setFreeze(boolean freeze) {
    mFrozen = freeze;
  }

}
