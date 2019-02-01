package utils;

public class Direction extends Pair<Integer, Integer> {

  public Direction(int vertical, int horizontal) {
    super(vertical, horizontal);
  }

  public int getVerticalDelta() {
    return getA();
  }

  public int getHorizontalDelta() {
    return getB();
  }

}
