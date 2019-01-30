package utils;

public class Location extends Pair<Integer, Integer> {

  public Location(int row, int col) {
    super(row, col);
  }

  public Location getLeft() {
    return getIncrement(new Direction(0, -1));
  }

  public Location getRight() {
    return getIncrement(new Direction(0, 1));
  }

  public Location getAbove() {
    return getIncrement(new Direction(-1, 0));
  }

  public Location getBelow() {
    return getIncrement(new Direction(1, 0));
  }

  public Location getIncrement(Direction direction) {
    return new Location(getRow() + direction.getVerticalDelta(),
        getCol() + direction.getHorizontalDelta());
  }

  public int getRow() {
    return getA();
  }

  public int getCol() {
    return getB();
  }

}
