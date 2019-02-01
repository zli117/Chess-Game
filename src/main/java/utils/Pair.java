package utils;

public class Pair<A, B> {

  private A mA;
  private B mB;

  Pair(A a, B b) {
    mA = a;
    mB = b;
  }

  A getA() {
    return mA;
  }

  void setA(A a) {
    mA = a;
  }

  B getB() {
    return mB;
  }

  void setB(B b) {
    mB = b;
  }

  @Override
  public int hashCode() {
    return 13 * mA.hashCode() + 37 * mB.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    @SuppressWarnings("unchecked")
    Pair<A, B> other = (Pair<A, B>) o;
    return other.mA == mA && other.mB == mB;
  }

  @Override
  public String toString() {
    return "<" + mA.toString() + ", " + mB.toString() + ">";
  }

}

