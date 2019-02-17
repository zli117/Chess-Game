package view;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
    JButton undoButton = new JButton("Undo");
    undoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        controller.undo();
      }
    });
    undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    basePanel.add(undoButton);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle(title);
    setSize(400, 500);
    setResizable(false);

    // Set to center of the screen
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(dim.width / 2 - getSize().width / 2,
        dim.height / 2 - getSize().height / 2);
  }

}
