package model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import utils.Direction;
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
   */
  public Pawn(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
    mMovingUp = true;
    mMoved = false;
  }

  public boolean isMovingUp() {
    return mMovingUp;
  }

  public void setMovingUp(boolean movingUp) {
    mMovingUp = movingUp;
  }

  @Override
  public Set<Move> getMovesAndAttacks() {
    LinkedHashSet<Move> moves = new LinkedHashSet<>();
    Location current = getLocation();
    Direction moveDirection = new Direction(mMovingUp ? -1 : 1, 0);
    Location moveOnDirection = current.getIncrement(moveDirection);
    moves.add(new Move(current, moveOnDirection));
    if (!mMoved) {
      moves.add(new Move(current, moveOnDirection.getIncrement(moveDirection)));
    }
    Move rightAttack = new Move(current, moveOnDirection.getRight());
    rightAttack.setCanAttack(true);
    moves.add(rightAttack);
    Move leftAttack = new Move(current, moveOnDirection.getLeft());
    leftAttack.setCanAttack(true);
    moves.add(leftAttack);
    return Collections.unmodifiableSet(moves);
  }

}
