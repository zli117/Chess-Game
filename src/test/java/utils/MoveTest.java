package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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

  @Test
  public void testEquals() {
    Move move1 = new Move(new Location(1, 2), new Location(2, 3));
    Move move2 = new Move(new Location(1, 2), new Location(2, 3));

    assertEquals(move1, move2);
    move1.setIsAttack(true);
    assertNotEquals(move1, move2);

    Move move3 = new Move(new Location(2, 2), new Location(2, 3));
    assertNotEquals(move2, move3);
  }
}
