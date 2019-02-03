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
  public void attack() {
    mIsAttack = true;
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

  /**
   * Get the direction of this move.
   */
  public Vector getDirection() {
    Location toLocation = getTo();
    Location fromLocation = getFrom();
    return Vector.buildVectorFromLocations(fromLocation, toLocation);
  }

  @Override
  public boolean equals(Object o) {
    Move move = (Move) o;
    return super.equals(o) && move.mIsAttack == mIsAttack;
  }

  @Override
  public int hashCode() {
    return super.hashCode() + (mIsAttack ? 29 : 63);
  }

}
