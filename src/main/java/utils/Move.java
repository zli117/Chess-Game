package utils;

/**
 * The class represents a move from one location to another.
 */
public class Move extends Pair<Location, Location> {

  private boolean mIsAttack;

  /**
   * Construct a move.
   *
   * @param from The location it moves from
   * @param to   The location it moves to
   */
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

  /**
   * Checks whether two moves are the same. Will check whether they have same
   * start and end location. And whether the attack status are the same
   */
  @Override
  public boolean equals(Object o) {
    Move move = (Move) o;
    return super.equals(o) && move.mIsAttack == mIsAttack;
  }

  /**
   * Compute hashcode based on location and attack status.
   */
  @Override
  public int hashCode() {
    return super.hashCode() + (mIsAttack ? 29 : 63);
  }

  /**
   * Get the inverse move of the current move.
   */
  public Move inverseMove() {
    Move inverse = new Move(getTo(), getFrom());
    if (isAttack()) {
      inverse.attack();
    }
    return inverse;
  }

}
