package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import cs3500.pa04.model.Coord;
import org.junit.jupiter.api.Test;

/**
 * Represents the test for Coord class
 */
public class CoordTest {

  /**
   * Tests the getters of the coord class
   */
  @Test
  public void testGetters() {

    Coord testCoord = new Coord(0, 1);

    assertEquals(0, testCoord.getX());
    assertEquals(1, testCoord.getY());
  }

  /**
   * Tests the equals() method by comparing coordinates with different memory location
   */
  @Test
  public void testEquals() {
    Coord testCoord = new Coord(0, 1);
    Coord testCoord2 = null;

    assertNotEquals(testCoord, testCoord2);
    assertNotEquals(testCoord, ShipType.CARRIER);
    assertEquals(testCoord, testCoord);
    assertEquals(testCoord, new Coord(0, 1));
  }


  /**
   * Tests the hashcode method by checking  that two different
   * objects with the same field produce the same hash
   */
  @Test
  public void testHashCode() {
    Coord testCoord = new Coord(0, 1);
    Coord testCoord2 = new Coord(0, 1);

    assertEquals(testCoord2.hashCode(), testCoord.hashCode());
  }
}
