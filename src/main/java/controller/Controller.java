package controller;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import model.ChessBoardBase;
import model.Piece;
import model.Side;
import utils.Location;
import utils.Move;
import view.ChessBoard;
import view.ViewCallBack;

/**
 * The controller of MVC. Responsible for player turns, showing information on
 * the GUI etc.
 */
public class Controller implements ViewCallBack {

  private ChessBoard mChessBoardView;
  private ChessBoardBase mChessBoardModel;
  private HashMap<URL, Icon> mCachedIcon;
  private Map<Location, Move> mLegalMoves;
  private Side mCurrentSide;

  /**
   * Create a controller from the chess board and view.
   */
  public Controller(ChessBoardBase chessBoardModel,
      ChessBoard chessBoardView) {
    mChessBoardModel = chessBoardModel;
    mChessBoardView = chessBoardView;
    mCachedIcon = new HashMap<>();
    mChessBoardView.addCallBack(this);
    mCurrentSide = Side.values()[0];
    mLegalMoves = null;
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
   * Update the board ui.
   */
  public void boardRedraw() {
    int height = mChessBoardModel.getHeight();
    int width = mChessBoardModel.getWidth();
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
   * Called when a grid is clicked.
   */
  @Override
  public void gridClicked(Location location) {
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
      if (move != null && mChessBoardModel.movePiece(move)) {
        mCurrentSide = mCurrentSide.next();
      }
      mLegalMoves = null;
      mChessBoardView.resetAllColor();
      if (mChessBoardModel.checkStaleMate(mCurrentSide)) {
        System.out.println("Stalemate");
        System.exit(0);
      }
      if (mChessBoardModel.checkCheckMate(mCurrentSide)) {
        System.out.printf("Checkmate! %s lost\n", mCurrentSide);
      }
      if (mChessBoardModel.checkKingPossiblyUnderCheck(mCurrentSide)) {
        Location kingLocation = mChessBoardModel.getKing(mCurrentSide)
            .getLocation();
        mChessBoardView.showWarningColor(kingLocation);
      }
    }
    boardRedraw();
  }

}
