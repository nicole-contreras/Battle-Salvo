package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.NonSunkenShips;
import cs3500.pa04.model.Ship;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


/**
 * Tests for the NonSunkenShips class.
 */
public class NonSunkenShipsTest {

  /**
   * Tests the updateNonSunkenShip method.
   */
  @Test
  void testUpdateNonSunkenShip() {
    NonSunkenShips testSunkenShip = new NonSunkenShips();
    List<Ship> nonSunkenShips = new ArrayList<>();
    List<Coord> testLocation = new ArrayList<>();
    testLocation.add(new Coord(1, 1));
    Ship ship1 = new Ship(ShipType.CARRIER, testLocation);
    nonSunkenShips.add(ship1);

    List<Coord> location2 = new ArrayList<>();
    Ship ship2 = new Ship(ShipType.BATTLESHIP, location2);
    nonSunkenShips.add(ship2);

    NonSunkenShips.updateNonSunkenShip(nonSunkenShips);

    List<Ship> expectedNonSunkenShips = new ArrayList<>();
    expectedNonSunkenShips.add(ship1);

    assertEquals(expectedNonSunkenShips, nonSunkenShips);
  }
}
