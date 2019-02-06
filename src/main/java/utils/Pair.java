package utils;

/**
 * Generic class for a pair.
 *
 * @param <A> The first element.
 * @param <B> The second element.
 */
public class Pair<A, B> {

  private A mA;
  private B mB;

  /**
   * Construct a pair.
   *
   * @param a The value of the first element.
   * @param b The value of the second element.
   */
  public Pair(A a, B b) {
    mA = a;
    mB = b;
  }

  /**
   * Get the first element.
   */
  public A getA() {
    return mA;
  }

  /**
   * Set the first element.
   */
  public void setA(A a) {
    mA = a;
  }

  /**
   * Get the second element.
   */
  public B getB() {
    return mB;
  }

  /**
   * Set the second element.
   */
  public void setB(B b) {
    mB = b;
  }

  /**
   * Compute the hash code using the two elements.
   */
  @Override
  public int hashCode() {
    return 13 * mA.hashCode() + 37 * mB.hashCode();
  }

  /**
   * Check whether two pairs are the same. They are the same iff the two
   * elements equal to the corresponding ones of the other object.
   */
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    @SuppressWarnings("unchecked")
    Pair<A, B> other = (Pair<A, B>) o;
    return other.mA.equals(mA) && other.mB.equals(mB);
  }

  /**
   * String representation of the element. It has the format: "(A_str, B_str)".
   */
  @Override
  public String toString() {
    return "(" + mA.toString() + ", " + mB.toString() + ")";
  }

}

