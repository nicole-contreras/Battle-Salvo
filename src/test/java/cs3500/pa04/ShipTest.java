package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


/**
 * Tests for the Ship class.
 */
public class ShipTest {

  /**
   * Tests the removeCoord method.
   */
  @Test
  void testRemoveCoord() {
    List<Coord> location = new ArrayList<>();
    location.add(new Coord(1, 1));
    location.add(new Coord(2, 2));
    location.add(new Coord(3, 3));
    assertEquals(3, location.size());

    Ship ship = new Ship(ShipType.CARRIER, location);
    ship.removeCoord(2, 2);

    assertEquals(2, location.size());

  }
}
