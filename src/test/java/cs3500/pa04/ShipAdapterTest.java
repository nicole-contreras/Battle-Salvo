package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Directions;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipAdapter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents the test for shipAdapter class
 */
public class ShipAdapterTest {
  private ShipAdapter shipAdapter;

  /**
   * Sets up the test
   */
  @BeforeEach
  public void setup() {
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(1, 1));
    coords.add(new Coord(1, 2));
    coords.add(new Coord(1, 3));
    shipAdapter = new ShipAdapter(new Ship(ShipType.BATTLESHIP, coords));
  }

  /**
   * Tests the getStart method
   */
  @Test
  public void testGetStart() {
    assertEquals(new Coord(1, 1), shipAdapter.getStart());
  }

  /**
   * Tests the getLength method
   */
  @Test
  public void testGetLength() {
    assertEquals(3, shipAdapter.getLength());
  }

  /**
   * Tests the getDire method
   */
  @Test
  public void testGetDirection() {
    assertEquals(Directions.VERTICAL, shipAdapter.getDire());
  }

}
