package view;

import java.awt.Color;
import javax.swing.JButton;

class ChessBoardGrid extends JButton {

  private Color mDefaultColor;
  private Color mSelectedColor;
  private Color mHintColor;
  private Color mWarningColor;

  /**
   * Create a chess board grid button.
   */
  public ChessBoardGrid() {
    super();

    // To prevent showing a different color when clicked down on a button
    setContentAreaFilled(false);
    setOpaque(true);

    mDefaultColor = new Color(0, 0, 0);
    mSelectedColor = new Color(0, 0, 0);
    mHintColor = new Color(0, 0, 0);
    mWarningColor = new Color(0, 0, 0);
  }

  /**
   * Set the default color of the piece.
   */
  public void setDefaultColor(Color defaultColor) {
    mDefaultColor = defaultColor;
  }

  /**
   * Set the background color of selected case.
   */
  public void setSelectedColor(Color selectedColor) {
    mSelectedColor = selectedColor;
  }

  /**
   * Set the color of warning.
   */
  public void setWarningColor(Color warningColor) {
    mWarningColor = warningColor;
  }

  /**
   * Set the color of hint.
   */
  public void setHintColor(Color hintColor) {
    mHintColor = hintColor;
  }

  /**
   * Reset the color back to default.
   */
  public void resetColor() {
    setBackground(mDefaultColor);
  }

  /**
   * Set the color to be selected color.
   */
  public void showSelected() {
    setBackground(mSelectedColor);
  }

  /**
   * Set the color to be warning color.
   */
  public void showWarning() {
    setBackground(mWarningColor);
  }

  public void showHint() {
    setBackground(mHintColor);
  }

}
