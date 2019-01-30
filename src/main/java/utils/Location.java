package utils;

public class Location extends Pair<Integer, Integer> {

  public Location(int row, int col) {
    super(row, col);
  }

  public Location getLeft() {
    return getIncrement(0, -1);
  }

  public Location getRight() {
    return getIncrement(0, 1);
  }

  public Location getAbove() {
    return getIncrement(-1, 0);
  }

  public Location getBelow() {
    return getIncrement(1, 0);
  }

  public Location getIncrement(int rowInc, int colInc) {
    return new Location(getA() + rowInc, getB() + colInc);
  }

  public int getRow() {
    return getA();
  }

  public int getCol() {
    return getB();
  }

}
