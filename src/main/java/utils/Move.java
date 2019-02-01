package utils;

public class Move extends Pair<Location, Location> {

  private boolean mIsAttack;

  public Move(Location from, Location to) {
    super(from, to);
    mIsAttack = false;
  }

  /**
   * Set whether the move is an attack move.
   */
  public void setIsAttack(boolean isAttack) {
    mIsAttack = isAttack;
  }

  /**
   * Check whether the move is an attack move. The attack move must attack a
   * piece.
   */
  public boolean isAttack() {
    return mIsAttack;
  }

  /**
   * Get where the move is from.
   */
  public Location getFrom() {
    return getA();
  }

  /**
   * Get where the move to to.
   */
  public Location getTo() {
    return getB();
  }

}
