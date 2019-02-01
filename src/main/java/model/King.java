package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import utils.Direction;
import utils.Location;
import utils.Move;

public class King extends BoundedPiece {

  private List<Location> mCastlingTo;

  public King(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
    mCastlingTo = new ArrayList<>();
  }

  @Override
  ArrayList<Direction> getRelativeLocations() {
    Direction[] relativeLocations = {
        new Direction(1, -1),
        new Direction(1, 0),
        new Direction(1, 1),
        new Direction(0, -1),
        new Direction(0, 1),
        new Direction(-1, -1),
        new Direction(-1, 0),
        new Direction(-1, 1)};

    return new ArrayList<>(Arrays.asList(relativeLocations));
  }

  @Override
  public void setLocation(Location location) {
    // Handling castling
    Location currLocation = getLocation();
    ChessBoardBase chessBoard = getChessBoard();
    if (mCastlingTo.contains(location)) {
      if (location.getCol() > currLocation.getCol()) {
        chessBoard.movePiece(
            new Move(
                new Location(currLocation.getRow(), chessBoard.getWidth() - 1),
                location.getLeft()));
      } else {
        chessBoard.movePiece(
            new Move(
                new Location(currLocation.getRow(), 0),
                location.getRight()));
      }
    }
    super.setLocation(location);
  }

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
      if (rightMost.toString().equals("Rook")
          && rightMost.getSide() == getSide()
          && !rightMost.hasMoved()) {
        boolean noPieceBetween = true;
        for (Location toRight = location.getRight();
            toRight != rightMost.getLocation();
            toRight = toRight.getRight()) {
          if (chessBoard.getPiece(toRight) != null) {
            noPieceBetween = false;
            break;
          }
        }
        if (noPieceBetween) {
          Move rightCastling = new Move(location,
              location.getRight().getRight());
          moves.add(rightCastling);
          mCastlingTo.add(rightCastling.getTo());
        }
      }

      Piece leftMost = chessBoard
          .getPiece(new Location(location.getRow(), 0));
      if (leftMost.toString().equals("Rook")
          && leftMost.getSide() == getSide()
          && !leftMost.hasMoved()) {
        boolean noPieceBetween = true;
        for (Location toLeft = location.getLeft();
            toLeft != leftMost.getLocation();
            toLeft = toLeft.getRight()) {
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

  @Override
  void modifyAdjustedMoves() {
    super.modifyAdjustedMoves();
    ChessBoardBase chessBoard = getChessBoard();

    // Remove any move that could get king checked
    chessBoard.withHoldPiece(getLocation());
    Set<Location> dangerousLocation = new HashSet<>();
    for (Piece opponent : chessBoard.getOpponentPieces(getSide())) {
      Set<Move> moves = opponent.getMovesAndAttacks();
      for (Move move : moves) {
        dangerousLocation.add(move.getTo());
      }
    }
    LinkedHashSet<Move> adjustedMoves = new LinkedHashSet<>();
    for (Move move : getAdjustedMoves()) {
      if (!dangerousLocation.contains(move.getTo())) {
        adjustedMoves.add(move);
      }
    }

    // Remove invalid castling because of passing though an enemy controlled
    // square
    Location currLocation = getLocation();
    if (dangerousLocation.contains(currLocation.getRight())) {
      adjustedMoves.remove(
          new Move(currLocation, currLocation.getRight().getRight()));
    }
    if (dangerousLocation.contains(currLocation.getLeft())) {
      adjustedMoves
          .remove(new Move(currLocation, currLocation.getRight().getLeft()));
    }

    setAdjustedMoves(adjustedMoves);
  }

}
