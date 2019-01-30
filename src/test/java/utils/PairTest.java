package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class PairTest {

  @Test
  public void testEquals() {
    Pair<Integer, Integer> pair0 = new Pair<>(10, 11);
    Pair<Integer, Integer> pair1 = new Pair<>(10, 11);
    assertEquals(pair0, pair1);
    pair1.setA(100);
    pair1.setB(-100);
    assertNotEquals(pair0, pair1);
    assertNotEquals(pair0, null);
    assertNotEquals(pair0, new Location(1, 2));
  }

  @Test
  public void testToString() {
    Pair<String, String> pair = new Pair<>("Hello", "World");
    assertEquals("<Hello, World>", pair.toString());
  }

  @Test
  public void testHashcode() {
    Pair<Integer, Integer> pair0 = new Pair<>(10, 11);
    Pair<Integer, Integer> pair1 = new Pair<>(11, 11);
    Pair<Integer, Integer> pair2 = new Pair<>(11, 10);
    Pair<Integer, Integer> pair4 = new Pair<>(10, 11);
    assertEquals(pair0.hashCode(), pair4.hashCode());
    assertNotEquals(pair0.hashCode(), pair1.hashCode());
    assertNotEquals(pair0.hashCode(), pair2.hashCode());
  }

}
