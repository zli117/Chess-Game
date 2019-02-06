package utils;

/**
 * Abstraction for a location.
 */
public class Location extends Pair<Integer, Integer> {

  /**
   * Construct a location from row index and column index. Row index is counted
   * from top of the board, starting at 0 Column index is counted from left of
   * the board, starting at 0.
   */
  public Location(int row, int col) {
    super(row, col);
  }

  /**
   * Construct a location that's left of the current location.
   */
  public Location getLeft() {
    return getIncrement(new Vector(0, -1));
  }

  /**
   * Construct a location that's right of the current location.
   */
  public Location getRight() {
    return getIncrement(new Vector(0, 1));
  }

  /**
   * Construct a location that's above the current location.
   */
  public Location getAbove() {
    return getIncrement(new Vector(-1, 0));
  }

  /**
   * Construct a location that's below the current location.
   */
  public Location getBelow() {
    return getIncrement(new Vector(1, 0));
  }

  /**
   * Get a location in a direction.
   *
   * @param direction The direction of the location.
   */
  public Location getIncrement(Vector direction) {
    return new Location(getRow() + direction.getVerticalDelta(),
        getCol() + direction.getHorizontalDelta());
  }

  /**
   * Get the row index.
   */
  public int getRow() {
    return getA();
  }

  /**
   * Get the column index.
   */
  public int getCol() {
    return getB();
  }

}
