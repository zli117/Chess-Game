package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import utils.Side;

public class Window extends JFrame {

  private JMenuItem mUndoMove;
  private JLabel mCurrentSide;
  private JLabel[] mScores;
  private WindowCallBack mCallback;

  /**
   * Create a window with title and chess board widget.
   */
  public Window(String title, ChessBoard chessBoard) {
    // Build the menu bar
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu gameMenu = new JMenu("Game");
    menuBar.add(fileMenu);
    menuBar.add(gameMenu);

    JMenuItem openConfig = new JMenuItem("Open config file");
    openConfig.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("Open a config file");
      }
    });
    fileMenu.add(openConfig);

    JMenuItem restartGame = new JMenuItem("Restart game");
    restartGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (mCallback != null) {
          mCallback.onRestart();
        }
      }
    });
    gameMenu.add(restartGame);

    mUndoMove = new JMenuItem("Undo last move");
    mUndoMove.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    mUndoMove.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (mCallback != null) {
          mCallback.onUndo();
        }
      }
    });
    gameMenu.add(mUndoMove);
    setJMenuBar(menuBar);

    JMenuItem forfeit = new JMenuItem("Forfeit");
    forfeit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        if (mCallback != null) {
          mCallback.onForfeit();
        }
      }
    });

    // Set up the content of the window
    Container contentPane = getContentPane();
    BoxLayout layout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
    contentPane.setLayout(layout);
    contentPane.add(chessBoard);

    JPanel infoPanel = new JPanel();
    int totalSides = Side.values().length;
    GridLayout infoLayout = new GridLayout(1, 1 + totalSides);
    infoPanel.setLayout(infoLayout);
    mCurrentSide = new JLabel();
    mScores = new JLabel[totalSides];
    infoPanel.add(mCurrentSide);
    Font font = mCurrentSide.getFont();
    float size = font.getSize() + 3.0f;
    mCurrentSide.setFont(font.deriveFont(size));
    mCurrentSide.setText(" ");
    for (int i = 0; i < mScores.length; ++i) {
      JLabel label = new JLabel();
      mScores[i] = label;
      infoPanel.add(label);
      label.setFont(font.deriveFont(size));
    }

    contentPane.add(infoPanel);

    pack();

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setTitle(title);
    setResizable(false);

    // Set to center of the screen
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(dim.width / 2 - getSize().width / 2,
        dim.height / 2 - getSize().height / 2);
    mCallback = null;
  }

  public void setEnabledUndoButton(boolean enabled) {
    mUndoMove.setEnabled(enabled);
  }

  public void setCallBack(WindowCallBack callBack) {
    mCallback = callBack;
  }

  public void setCurrentSide(Side side) {
    mCurrentSide.setText(String.format("Current side: %s", side));
  }

  public void setScore(Side side, int score) {
    JLabel label = mScores[side.ordinal()];
    label.setText(String.format("%s score: %d", side, score));
  }

}
