package view;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;

class ChessBoardGrid extends JButton {

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

    mSelectedColor = new Color(0, 0, 0);
    mHintColor = new Color(0, 0, 0);
    mWarningColor = new Color(0, 0, 0);
  }

  /**
   * Set the background color of selected case.
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
  }

  /**
   * Set the color to be selected color.
   */
  void showSelected() {
    setBorderPainted(true);
    setBorder(BorderFactory.createLineBorder(mSelectedColor, 5));
  }

  /**
   * Set the color to be warning color.
   */
  void showWarning() {
    setBorderPainted(true);
    setBorder(BorderFactory.createLineBorder(mWarningColor, 5));
  }

  void showHint() {
    setBorderPainted(true);
    setBorder(BorderFactory.createLineBorder(mHintColor, 5));
  }

}
