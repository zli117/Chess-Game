package view;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;

class ChessBoardGrid extends JButton {

  private Color mBackgroundColor;
  private Color mSelectedColor;
  private Color mHintColor;
  private Color mWarningColor;

  /**
   * Create a chess board grid button.
   */
  ChessBoardGrid() {
    super();

    // To prevent showing a different color when clicked down on a button
    setContentAreaFilled(false);
    setOpaque(true);

    mBackgroundColor = new Color(0, 0, 0);
    mSelectedColor = new Color(0, 0, 0);
    mHintColor = new Color(0, 0, 0);
    mWarningColor = new Color(0, 0, 0);
  }

  void setBackgroundColor(Color backgroundColor) {
    mBackgroundColor = backgroundColor;
    setBackground(mBackgroundColor);
  }

  /**
   * Set the color of the grid if it has been selected.
   */
  void setSelectedColor(Color selectedColor) {
    mSelectedColor = selectedColor;
  }

  /**
   * Set the color of warning.
   */
  void setWarningColor(Color warningColor) {
    mWarningColor = warningColor;
  }

  /**
   * Set the color of hint.
   */
  void setHintColor(Color hintColor) {
    mHintColor = hintColor;
  }

  /**
   * Reset the color back to default.
   */
  void resetColor() {
    setBorderPainted(false);
    setBackground(mBackgroundColor);
  }

  /**
   * Set the color to be selected color.
   */
  void showSelected() {
    setBackground(mSelectedColor);
  }

  /**
   * Set the color to be warning color.
   */
  void showWarning() {
    setBackground(mWarningColor);
  }

  /**
   * Show the hint at the border of the legal target grids.
   */
  void showHint() {
    setBorderPainted(true);
    setBorder(BorderFactory.createLineBorder(mHintColor, 5));
  }

}
