package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import utils.Direction;
import utils.Location;
import utils.Move;

public class King extends BoundedPiece {

  public King(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
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

    // TODO: Support castling

    return new ArrayList<>(Arrays.asList(relativeLocations));
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
    setAdjustedMoves(adjustedMoves);
  }
}
