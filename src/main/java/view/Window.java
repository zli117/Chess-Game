package view;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Window extends JFrame {

  /**
   * Create a window with title and chess board widget.
   */
  public Window(String title, ChessBoard chessBoard, Controller controller) {
    JPanel basePanel = new JPanel();
    basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
    basePanel.add(chessBoard);
    setLayout(new BorderLayout());
    add(basePanel);
    JToggleButton toggleButton = new JToggleButton("Enable Move");
    toggleButton.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent changeEvent) {
        controller.toggleMoves();
      }
    });
    toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    basePanel.add(toggleButton);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle(title);
    setSize(450, 500);
    setResizable(false);

    // Set to center of the screen
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(dim.width / 2 - getSize().width / 2,
        dim.height / 2 - getSize().height / 2);
  }

}
