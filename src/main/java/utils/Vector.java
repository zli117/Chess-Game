package utils;

public class Vector extends Pair<Integer, Integer> {

  public Vector(int vertical, int horizontal) {
    super(vertical, horizontal);
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
  public boolean checkSameDirection(Vector other) {
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

}
