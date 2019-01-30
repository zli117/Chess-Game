package utils;

public class Move extends Pair<Location, Location> {

  private boolean mIsAttack;

  public Move(Location from, Location to) {
    super(from, to);
    mIsAttack = false;
  }

  public void setIsAttack(boolean isAttack) {
    mIsAttack = isAttack;
  }

  public boolean isAttack() {
    return mIsAttack;
  }

  public Location getFrom() {
    return getA();
  }

  public Location getTo() {
    return getB();
  }

}
