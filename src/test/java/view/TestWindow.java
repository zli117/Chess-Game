package view;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A window to show the GUI for testing.
 */
class TestWindow extends JFrame {

  /**
   * Create a window from the container of the GUI, the test instructions and
   * the test environment.
   */
  TestWindow(Container testUI, String testScript,
      ManualTestEnv env) {
    JPanel basePanel = new JPanel();
    basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    JTextArea testScripArea = new JTextArea();
    testScripArea.setEditable(false);
    testScripArea.setLineWrap(true);
    testScripArea.setText("Test instructions: " + testScript);
    testScripArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(testScripArea);
    basePanel.add(testUI);
    basePanel.add(buttonPanel);
    basePanel.add(scrollPane);
    add(basePanel);
    JButton passButton = new JButton("Pass");
    passButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        setVisible(false);
        env.getContainerThread().interrupt();
      }
    });
    JButton failButton = new JButton("Fail");
    failButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        setVisible(false);
        env.setFailed();
        env.getContainerThread().interrupt();
      }
    });
    buttonPanel.add(passButton);
    buttonPanel.add(failButton);
    setTitle("Test");
    setSize(600, 600);
    setResizable(false);
    // Basically you can't close the window without pass or fail.
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    // Set to center of the screen
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(dim.width / 2 - getSize().width / 2,
        dim.height / 2 - getSize().height / 2);
  }

}
