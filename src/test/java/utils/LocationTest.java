package utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LocationTest {

  @Test
  public void testGetLeft() {
    Location location = new Location(10, 11);
    Location left = location.getLeft();
    assertEquals(10, left.getRow());
    assertEquals(10, left.getCol());
  }

  @Test
  public void testGetRight() {
    Location location = new Location(10, 11);
    Location right = location.getRight();
    assertEquals(10, right.getRow());
    assertEquals(12, right.getCol());
  }

  @Test
  public void testGetAbove() {
    Location location = new Location(10, 11);
    Location above = location.getAbove();
    assertEquals(9, above.getRow());
    assertEquals(11, above.getCol());
  }

  @Test
  public void testGetBelow() {
    Location location = new Location(10, 11);
    Location below = location.getBelow();
    assertEquals(11, below.getRow());
    assertEquals(11, below.getCol());
  }

  @Test
  public void testGetIncrement() {
    Location location = new Location(10, 11);
    Location newLocation = new Location(20, -10);
    assertEquals(newLocation, location.getIncrement(new Direction(10, -21)));
  }

}
