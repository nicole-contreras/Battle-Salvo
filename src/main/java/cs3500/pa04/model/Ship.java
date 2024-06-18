package cs3500.pa04.model;

import cs3500.pa04.ShipType;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a ship in the game.
 */
public class Ship {
  private final ShipType type;
  private final List<Coord> location;

  /**
   * Initializes a ship with its model and where its placed
   *
   * @param type the model of the ship
   * @param location the list of coords where it is located
   */
  public Ship(ShipType type, List<Coord> location) {
    this.type = type;
    this.location = location;
  }

  /**
   * Returns the location of the ship.
   *
   * @return the location of the ship
   */
  public List<Coord> getLocation() {
    return location;
  }

  /**
   * Returns the type of the ship.
   *
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public void removeCoord(int x, int y) {
    List<Coord> coordToRemove = new ArrayList<>();
    for (Coord c : location) {
      if (c.getX() == x && c.getY() == y) {
        coordToRemove.add(c);
      }
    }
    removeCoordHelper(coordToRemove);
  }

  /**
   * Returns the type of the ship.
   *
   * @param coordToRemove the list of coordinates to remove
   */
  private void removeCoordHelper(List<Coord> coordToRemove) {
    for (Coord c : coordToRemove) {
      location.remove(c);
    }

  }

}
