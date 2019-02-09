package model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import utils.Location;
import utils.Move;
import utils.Vector;

/**
 * King piece of chess.
 */
public class King extends Piece {

  private List<Location> mCastlingTo;

  /**
   * Create a king.
   */
  public King(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
    mCastlingTo = new ArrayList<>();
  }

  /**
   * King moves one step each time. See Wikipedia for king's moves.
   */
  @Override
  public List<Vector> getOneStepOffsets() {
    Vector[] relativeLocations = {
        new Vector(1, -1),
        new Vector(1, 0),
        new Vector(1, 1),
        new Vector(0, -1),
        new Vector(0, 1),
        new Vector(-1, -1),
        new Vector(-1, 0),
        new Vector(-1, 1)};

    return new ArrayList<>(Arrays.asList(relativeLocations));
  }

  /**
   * Extra logic when king has moved. If the location moved to is actually a
   * castling location, will move the corresponding rook.
   */
  @Override
  public void setLocation(Location location) {
    // Handling castling
    Location currLocation = getLocation();
    ChessBoardBase chessBoard = getChessBoard();
    if (currLocation != null && location != null) {
      if (mCastlingTo.contains(location)) {
        if (location.getCol() > currLocation.getCol()) {
          chessBoard.moveWithOutCheck(
              new Move(
                  new Location(currLocation.getRow(),
                      chessBoard.getWidth() - 1),
                  location.getLeft()));
        } else {
          chessBoard.moveWithOutCheck(
              new Move(
                  new Location(currLocation.getRow(), 0),
                  location.getRight()));
        }
      }
    }
    super.setLocation(location);
  }

  /**
   * Extra logic for validating and inserting the moves for castling.
   */
  @Override
  public Set<Move> getMovesAndAttacks() {
    Set<Move> moves = new LinkedHashSet<>(super.getMovesAndAttacks());

    // Castling
    mCastlingTo.clear();
    Location location = getLocation();
    if (!hasMoved()) {
      // TODO: Clean up the duplicate code
      ChessBoardBase chessBoard = getChessBoard();
      Piece rightMost = chessBoard
          .getPiece(new Location(location.getRow(), chessBoard.getWidth() - 1));
      if (rightMost instanceof Rook
          && rightMost.getSide() == getSide()
          && !rightMost.hasMoved()) {
        boolean noPieceBetween = true;
        for (Location toRight = location.getRight();
            !toRight.equals(rightMost.getLocation());
            toRight = toRight.getRight()) {
          if (chessBoard.getPiece(toRight) != null) {
            noPieceBetween = false;
            break;
          }
        }
        if (noPieceBetween) {
          Move rightCastling = new Move(location,
              location.getRight().getRight());
          // The move is not an attack, since there's no piece in between
          moves.add(rightCastling);
          mCastlingTo.add(rightCastling.getTo());
        }
      }

      Piece leftMost = chessBoard
          .getPiece(new Location(location.getRow(), 0));
      if (leftMost instanceof Rook
          && leftMost.getSide() == getSide()
          && !leftMost.hasMoved()) {
        boolean noPieceBetween = true;
        for (Location toLeft = location.getLeft();
            !toLeft.equals(leftMost.getLocation());
            toLeft = toLeft.getLeft()) {
          if (chessBoard.getPiece(toLeft) != null) {
            noPieceBetween = false;
            break;
          }
        }
        if (noPieceBetween) {
          Move leftCastling = new Move(location, location.getLeft().getLeft());
          moves.add(leftCastling);
          mCastlingTo.add(leftCastling.getTo());
        }
      }
    }
    return moves;
  }

  /**
   * Extra logic for removing illegal moves for castling. A move for castling is
   * invalid if it's under control of an opponent piece or king will pass
   * through a location under opponents' control.
   */
  @Override
  void modifyAdjustedMoves() {
    super.modifyAdjustedMoves();
    ChessBoardBase chessBoard = getChessBoard();

    // Collect all the possible attacks of opponent.
    chessBoard.withHoldPiece(getLocation());
    Set<Location> dangerousLocation = new HashSet<>();
    for (Piece opponent : chessBoard.getOpponentPieces(getSide())) {
      Set<Move> moves = opponent.getMovesAndAttacks();
      for (Move move : moves) {
        // Only attack moves are dangerous
        if (move.isAttack()) {
          dangerousLocation.add(move.getTo());
        }
      }
    }
    LinkedHashSet<Move> adjustedMoves = new LinkedHashSet<>(getAdjustedMoves());

    // Remove invalid castling because of passing though an enemy controlled
    // square
    Location currLocation = getLocation();
    if (dangerousLocation.contains(currLocation.getRight())) {
      adjustedMoves.remove(
          new Move(currLocation, currLocation.getRight().getRight()));
    }
    if (dangerousLocation.contains(currLocation.getLeft())) {
      adjustedMoves
          .remove(new Move(currLocation, currLocation.getLeft().getLeft()));
    }

    setAdjustedMoves(adjustedMoves);
    chessBoard.restoreWithHold();
  }

  /**
   * Get the icon for king.
   */
  @Override
  public URL getImageResourceUrl() {
    if (getSide() == Side.White) {
      return getClass().getResource("/images/45px-Chess_klt45.svg.png");
    } else {
      return getClass().getResource("/images/45px-Chess_kdt45.svg.png");
    }
  }

}
