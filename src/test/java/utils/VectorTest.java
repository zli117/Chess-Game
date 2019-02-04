package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class VectorTest {

  @RunWith(Parameterized.class)
  public static class TestGetDelta {

    @Parameter
    public int mVertical;
    @Parameter(1)
    public int mHorizontal;

    /**
     * Parameters for the test.
     */
    @Parameters
    public static Collection<Object[]> data() {
      return Arrays.asList(new Object[][]{
          {0, 0}, {-1, 1}, {2, -1}, {Integer.MAX_VALUE, 2},
          {4, Integer.MAX_VALUE}, {Integer.MIN_VALUE, 5}, {6, Integer.MIN_VALUE}
      });
    }

    @Test
    public void testGetDelta() {
      Vector vector = new Vector(mVertical, mHorizontal);
      assertEquals(mVertical, vector.getVerticalDelta());
      assertEquals(mHorizontal, vector.getHorizontalDelta());
    }

  }

  @RunWith(Parameterized.class)
  public static class TestSameDirection {

    @Parameter
    public int mVertical;
    @Parameter(1)
    public int mHorizontal;
    @Parameter(2)
    public int mSameVertical;
    @Parameter(3)
    public int mSameHorizontal;
    @Parameter(4)
    public int mDifferentVertical;
    @Parameter(5)
    public int mDifferentHorizontal;


    /**
     * Parameters for the test.
     */
    @Parameters
    public static Collection<Object[]> data() {
      return Arrays.asList(new Object[][]{
          {0, 0, 0, 0, 1, 1}, {1, 1, 2, 2, 2, 3}, {1, 1, -1, -1, 1, -1},
          {0, 1, 0, 2, 1, 1}, {-10, 10, 1, -1, 20, 20}
      });
    }

    @Test
    public void testCheckParallel() {
      Vector vector1 = new Vector(mVertical, mHorizontal);
      Vector vector2 = new Vector(mSameVertical, mSameHorizontal);
      Vector vector3 = new Vector(mDifferentVertical, mDifferentHorizontal);

      assertTrue(vector1.checkParallel(vector1));
      assertTrue(vector2.checkParallel(vector2));
      assertTrue(vector3.checkParallel(vector3));
      assertTrue(vector1.checkParallel(vector2));
      assertTrue(vector2.checkParallel(vector1));
      assertFalse(vector1.checkParallel(vector3));
      assertFalse(vector3.checkParallel(vector1));
      assertFalse(vector3.checkParallel(vector2));
    }

  }

}