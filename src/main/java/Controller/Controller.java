package Controller;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import model.ChessBoardBase;
import model.GameObserverCallBacks;
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
public class Controller implements GameObserverCallBacks, ViewCallBack {

  private ChessBoard mChessBoardView;
  private ChessBoardBase mChessBoardModel;
  private HashMap<URL, Icon> mCachedIcon;
  private Map<Location, Move> mLegalMoves;
  private Side mCurrentSide;

  public Controller(ChessBoardBase chessBoardModel, ChessBoard chessBoardView) {
    mChessBoardModel = chessBoardModel;
    mChessBoardView = chessBoardView;
    mCachedIcon = new HashMap<>();
    mChessBoardView.addCallBack(this);
    mChessBoardModel.registerObserver(this);
    mCurrentSide = Side.values()[0];
    mLegalMoves = null;
  }

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

  public void boardRedraw() {
    int height = mChessBoardModel.getHeight();
    int width = mChessBoardModel.getWidth();
    for (int i = 0; i < height; ++i) {
      for (int j = 0; j < width; ++j) {
        Location location = new Location(i, j);
        Piece piece = mChessBoardModel.getPiece(location);
        URL imageURL;
        if (piece == null || (imageURL = piece.getImageResourceURL()) == null) {
          mChessBoardView.setIcon(location, null);
        } else {
          Icon icon = loadIcon(imageURL);
          mChessBoardView.setIcon(location, icon);
        }
      }
    }
  }

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
      System.out.printf("Going to move: %s", move);
      // Not allowed to implement in this checkpoint.
//      if (move != null && mChessBoardModel.movePiece(move)) {
//        System.out.printf("Move %s performed\n", move);
//        mCurrentSide = mCurrentSide.next();
//      }
      mLegalMoves = null;
      mChessBoardView.resetAllColor();
    }
  }

  @Override
  public void pieceMoved(Move move) {
    boardRedraw();
  }

  @Override
  public void pieceRemoved(Piece pieceRemoved, Location originalLocation) {
    boardRedraw();
  }

}
