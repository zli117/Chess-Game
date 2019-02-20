package utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SideTest {

  @Test
  public void testNext() {
    assertEquals(Side.White, Side.Black.next());
    assertEquals(Side.Black, Side.White.next());
  }

}