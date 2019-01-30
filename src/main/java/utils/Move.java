package utils;

public class Move extends Pair<Location, Location> {

  private boolean mCanAttack;

  public Move(Location from, Location to) {
    super(from, to);
    mCanAttack = false;
  }

  public void setCanAttack(boolean canAttack) {
    mCanAttack = canAttack;
  }

  public boolean canAttack() {
    return mCanAttack;
  }

  public Location getFrom() {
    return getA();
  }

  public Location getTo() {
    return getB();
  }

}
