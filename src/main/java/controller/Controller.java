package controller;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import model.ChessBoardBase;
import model.Command;
import model.Piece;
import utils.BoardBuilder;
import utils.Location;
import utils.Move;
import utils.Side;
import view.ChessBoard;
import view.ChessBoardCallBack;
import view.WindowCallBack;
import view.WindowInterface;

/**
 * The controller of MVC. Responsible for player turns, showing information on
 * the GUI etc.
 */
public class Controller implements ChessBoardCallBack, WindowCallBack {

  private URL mConfigPath;
  private ChessBoard mChessBoardView;
  private ChessBoardBase mChessBoardModel;
  private WindowInterface mWindow;
  private HashMap<URL, Icon> mCachedIcon;
  private Map<Location, Move> mLegalMoves;
  private Side mCurrentSide;
  private int[] mScores;
  private Stack<Command> mCommands;

  /**
   * Create a controller from the chess board and view.
   */
  public Controller(WindowInterface window) {
    mConfigPath = null;
    mWindow = window;
    mWindow.setEnabledUndoButton(false);
    mWindow.setCallBack(this);
    mChessBoardModel = null;
    mChessBoardView = mWindow.getChessBoard();
    mCachedIcon = new HashMap<>();
    mChessBoardView.setCallBack(this);
    setCurrentSide(Side.values()[0]);
    mLegalMoves = null;
    mCommands = new Stack<>();
    mScores = new int[Side.values().length];
    for (int i = 0; i < mScores.length; ++i) {
      setScore(Side.values()[i], 0);
    }
  }

  /**
   * Load config from URL.
   *
   * @param configPath URL to the config file.
   * @return True if successful, false otherwise.
   */
  public boolean loadConfig(URL configPath) {
    try {
      mChessBoardModel = BoardBuilder.constructFromFile(configPath);
      if (mChessBoardModel.getHeight() != mChessBoardView.getGridRows()
          || mChessBoardModel.getWidth() != mChessBoardView.getGridRows()) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    mConfigPath = configPath;

    mChessBoardView.resetAllColor();
    boardRedraw();
    setCurrentSide(Side.values()[0]);
    mCommands = new Stack<>();
    mWindow.setEnabledUndoButton(false);
    return true;
  }

  /**
   * Set the current side and update the UI.
   */
  private void setCurrentSide(Side side) {
    mCurrentSide = side;
    if (mWindow != null) {
      mWindow.setCurrentSide(side);
    }
  }

  /**
   * Set current score of the side and update the UI.
   */
  private void setScore(Side side, int score) {
    mScores[side.ordinal()] = score;
    if (mWindow != null) {
      mWindow.setScore(side, score);
    }
  }

  /**
   * Load an icon from url.
   */
  private Icon loadIcon(URL url) {
    if (!mCachedIcon.containsKey(url)) {
      try {
        BufferedImage image = ImageIO.read(url);
        ImageIcon imageIcon = new ImageIcon(image);
        mCachedIcon.put(url, imageIcon);
      } catch (Exception e) {
        return null;
      }
    }
    return mCachedIcon.get(url);
  }

  /**
   * Update the board view.
   */
  private void boardRedraw() {
    int height = mChessBoardView.getGridRows();
    int width = mChessBoardView.getGridCols();
    for (int i = 0; i < height; ++i) {
      for (int j = 0; j < width; ++j) {
        Location location = new Location(i, j);
        Piece piece = mChessBoardModel.getPiece(location);
        URL imageUrl;
        if (piece == null || (imageUrl = piece.getImageResourceUrl()) == null) {
          mChessBoardView.setIcon(location, null);
        } else {
          Icon icon = loadIcon(imageUrl);
          mChessBoardView.setIcon(location, icon);
        }
      }
    }
  }

  /**
   * Increment scores by 1.
   *
   * @param skipCurrent If true, only increment opponents' score. If false,
   *                    increment everyone' score.
   */
  private void incrementScore(boolean skipCurrent) {
    for (int i = 0; i < mScores.length; ++i) {
      Side side = Side.values()[i];
      if (!skipCurrent || side != mCurrentSide) {
        setScore(side, mScores[i] + 1);
      }
    }
  }

  /**
   * Called when a grid is clicked.
   */
  @Override
  public void gridClicked(Location location) {
    if (mChessBoardModel == null) {
      return;
    }
    if (mLegalMoves == null) {
      if (mChessBoardModel.getSideOfLocation(location) == mCurrentSide) {
        mLegalMoves = new HashMap<>(); // Maps target location to the move.
        Set<Move> moves = mChessBoardModel.getLegalMoves(location);
        if (moves != null) {
          for (Move move : moves) {
            mChessBoardView.showHintColor(move.getTo());
            mLegalMoves.put(move.getTo(), move);
          }
        }
        mChessBoardView.showSelectedColor(location);
      }
    } else {
      Move move = mLegalMoves.get(location);
      // Not allowed to implement in this checkpoint.
      mChessBoardView.resetAllColor();
      mLegalMoves = null;
      Command command = new Command(move, mChessBoardModel);
      command.setSide(mCurrentSide);
      if (move != null && command.execute()) {
        setCurrentSide(mCurrentSide.next());
        mCommands.push(command);
        mWindow.setEnabledUndoButton(true);
      }
    }
    boardRedraw();
    if (mChessBoardModel.checkKingPossiblyUnderCheck(mCurrentSide)) {
      Location kingLocation = mChessBoardModel.getKing(mCurrentSide)
          .getLocation();
      mChessBoardView.showWarningColor(kingLocation);
    }
    if (mChessBoardModel.checkStaleMate(mCurrentSide)) {
      incrementScore(false);
      mWindow.showStalemate();
    }
    if (mChessBoardModel.checkCheckMate(mCurrentSide)) {
      incrementScore(true);
      mWindow.showCheckmate(mCurrentSide);
    }
  }

  @Override
  public void onUndo() {
    if (!mCommands.isEmpty()) {
      Command command = mCommands.pop();
      if (mCommands.isEmpty()) {
        mWindow.setEnabledUndoButton(false);
      }
      command.undo();
      mLegalMoves = null;
      if (command.hasSide()) {
        setCurrentSide(command.getSide());
      }
      mChessBoardView.resetAllColor();
      boardRedraw();
    }
  }

  @Override
  public void onRestart(boolean isTie) {
    loadConfig(mConfigPath);
    if (isTie) {
      incrementScore(false);
    }
  }

  @Override
  public void onOpenConfig(URL fileUrl) {
    if (!loadConfig(fileUrl)) {
      mWindow.showErrorDialog("Invalid config file");
    }
  }

  @Override
  public void onForfeit() {
    loadConfig(mConfigPath);
    incrementScore(true);
  }

  public int getScore(Side side) {
    return mScores[side.ordinal()];
  }

}
