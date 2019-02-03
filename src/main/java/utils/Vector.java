package utils;

public class Vector extends Pair<Integer, Integer> {

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
    return new Vector(to.getRow() - from.getRow(),
        to.getCol() - from.getCol());
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

    return (myHorizontal == 0
        || (myVertical / myHorizontal == otherVertical / otherHorizontal));
  }

  /**
   * Check whether the two vectors are parallel and point to the same
   * direction.
   *
   * @param other The other vector
   */
  public boolean checkSameLocation(Vector other) {
    Vector meNormalized = getNormalized();
    Vector otherNormalized = other.getNormalized();
    return meNormalized.equals(otherNormalized);
  }

  /**
   * Helper function for computing the gcd of two numbers.
   */
  private int gcd(int a, int b) {
    if (b == 0) {
      return a;
    }
    return gcd(b, a % b);
  }

  /**
   * Reduce the vector to the smallest length in the same direction.
   */
  public Vector getNormalized() {
    int gcd = gcd(Math.abs(getVerticalDelta()), Math.abs(getHorizontalDelta()));
    return new Vector(getVerticalDelta() / gcd, getHorizontalDelta() / gcd);
  }

}
