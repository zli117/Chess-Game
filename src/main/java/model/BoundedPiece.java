package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import utils.Direction;
import utils.Location;
import utils.Move;

public abstract class BoundedPiece extends Piece {

  BoundedPiece(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  abstract ArrayList<Direction> getRelativeLocations();

  @Override
  public Set<Move> getMovesAndAttacks() {
    LinkedHashSet<Move> moves = new LinkedHashSet<>();
    ArrayList<Direction> relativeLocations = getRelativeLocations();
    Location current = getLocation();
    for (Direction relativeLocation : relativeLocations) {
      Move attackMove = new Move(current,
          current.getIncrement(relativeLocation));
      attackMove.setIsAttack(true);
      moves.add(attackMove);
      // Same reason as unbounded piece. The move is an optional attack.
      Move move = new Move(current,
          current.getIncrement(relativeLocation));
      moves.add(move);
    }
    return Collections.unmodifiableSet(moves);
  }

}
