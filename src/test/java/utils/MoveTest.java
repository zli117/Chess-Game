package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MoveTest {

  @Test
  public void testSetIsAttackAndIsAttack() {
    Location dummyLocation = new Location(0, 0);
    Move move = new Move(dummyLocation, dummyLocation);
    assertFalse(move.isAttack());
    move.setIsAttack(true);
    assertTrue(move.isAttack());
    move.setIsAttack(false);
    assertFalse(move.isAttack());
  }

  @Test
  public void testGetFromAndGetTo() {
    Location from = new Location(1, 2);
    Location to = new Location(3, 4);
    Move move = new Move(from, to);
    assertEquals(from, move.getFrom());
    assertEquals(to, move.getTo());
  }

}