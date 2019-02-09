package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {

  /**
   * Create a window with title and chess board widget.
   */
  public Window(String title, ChessBoard chessBoard) {
    JPanel basePanel = new JPanel();
    basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.LINE_AXIS));
    basePanel.add(chessBoard);
    setLayout(new BorderLayout());
    add(basePanel);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle(title);
    setMinimumSize(chessBoard.getMinimumSize());
    setMaximumSize(chessBoard.getMaximumSize());
    setSize(chessBoard.getMaximumSize());
    setResizable(false);

    // Set to center of the screen
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(dim.width / 2 - getSize().width / 2,
        dim.height / 2 - getSize().height / 2);
  }

}
