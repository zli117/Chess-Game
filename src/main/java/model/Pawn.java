package model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import utils.Location;
import utils.Move;

public class Pawn extends Piece {

  private boolean mMovingUp;
  private boolean mMoved;

  /**
   * Create a pawn.
   *
   * @param chessBoard The chess board it belongs to
   * @param side       Which side it belongs to
   * @param movingUp   Whether it's moving up or down
   */
  public Pawn(ChessBoardBase chessBoard, Side side, boolean movingUp) {
    super(chessBoard, side);
    mMovingUp = movingUp;
    mMoved = false;
  }

  @Override
  public void setLocation(Location location) {
    Location currentLocation = getLocation();
    if (location != null) {
      if (currentLocation != null && currentLocation != location) {
        mMoved = true;
      }
    }
    super.setLocation(location);
  }

  @Override
  public Set<Move> getMovesAndAttacks() {
    LinkedHashSet<Move> moves = new LinkedHashSet<>();
    Location current = getLocation();
    moves.add(
        new Move(current, mMovingUp ? current.getAbove() : current.getBelow()));
    if (!mMoved) {
      moves.add(new Move(current, mMovingUp ? current.getAbove().getAbove()
          : current.getBelow().getBelow()));
    }
    Move attack = new Move(current, current.getAbove().getRight());
    attack.setCanAttack(true);
    moves.add(attack);
    return Collections.unmodifiableSet(moves);
  }

}
