package view;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
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
  TestWindow(Container testUI, String testScript, ManualTestEnv env,
      String testName) {
    Container contentPane = getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

    JTextArea testScripArea = new JTextArea();
    testScripArea.setEditable(false);
    testScripArea.setLineWrap(true);
    testScripArea.setText("Test instructions: " + testScript);
    testScripArea.setWrapStyleWord(true);
    Font font = testScripArea.getFont();
    float size = font.getSize() + 5.0f;
    testScripArea.setFont(font.deriveFont(size));
    testScripArea.setRows(5);

    JScrollPane scrollPane = new JScrollPane(testScripArea);

    contentPane.add(testUI);
    contentPane.add(buttonPanel);
    contentPane.add(scrollPane);
    JButton passButton = new JButton("Pass");
    passButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        setVisible(false);
        env.interruptContainerThread();
      }
    });
    JButton failButton = new JButton("Fail");
    failButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        setVisible(false);
        env.setFailed();
        env.interruptContainerThread();
      }
    });
    buttonPanel.add(passButton);
    buttonPanel.add(failButton);
    setTitle(testName);
    pack();
    setResizable(false);
    // Basically you can't close the window without pass or fail.
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    // Set to center of the screen
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(dim.width / 2 - getSize().width / 2,
        dim.height / 2 - getSize().height / 2);
  }

}
