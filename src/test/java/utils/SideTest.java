package utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import utils.Side;

public class SideTest {

  @Test
  public void testNext() {
    assertEquals(Side.White, Side.Black.next());
    assertEquals(Side.Black, Side.White.next());
  }

}