package utils;

/**
 * Represents a directed change of location.
 */
public class Vector extends Pair<Integer, Integer> {

  /**
   * Construct a vector with vertical change and horizontal change.
   *
   * @param vertical   The vertical increment.
   * @param horizontal The horizontal increment.
   */
  public Vector(int vertical, int horizontal) {
    super(vertical, horizontal);
  }

  /**
   * Create a vector from two locations.
   *
   * @param from The source location
   * @param to   The target location
   */
  public static Vector buildVectorFromLocations(Location from, Location to) {
    return new Vector(to.getRow() - from.getRow(), to.getCol() - from.getCol());
  }

  /**
   * Get the vertical displacement.
   */
  public int getVerticalDelta() {
    return getA();
  }

  /**
   * Get the horizontal displacement.
   */
  public int getHorizontalDelta() {
    return getB();
  }

  /**
   * Check if two vectors are parallel.
   *
   * @param other Another vector
   */
  public boolean checkParallel(Vector other) {
    float myVertical = getVerticalDelta();
    float myHorizontal = getHorizontalDelta();
    float otherVertical = other.getVerticalDelta();
    float otherHorizontal = other.getHorizontalDelta();

    if (myHorizontal == 0 && !(otherHorizontal == 0)) {
      return false;
    }

    return (myHorizontal == 0 || (myVertical / myHorizontal
        == otherVertical / otherHorizontal));
  }

}
