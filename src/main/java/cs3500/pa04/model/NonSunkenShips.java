package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a class that updates the non-sunken ships.
 */
public class NonSunkenShips {
  /**
   * Updates the non-sunken ships.
   *
   * @param nonSunkenShips the non-sunken ships
   */
  public static void updateNonSunkenShip(List<Ship> nonSunkenShips) {
    List<Ship> shipsToRemove = new ArrayList<>();

    for (Ship ship : nonSunkenShips) {
      List<Coord> location = ship.getLocation();

      if (location.isEmpty()) {
        shipsToRemove.add(ship);
      }
    }

    nonSunkenShips.removeAll(shipsToRemove);
  }

}
