package cs3500.pa04.model;

import java.util.List;

/**
 * Represents a ship adapter to convert the ship class to the fields understood by the server
 */
public class ShipAdapter {

  private final Coord start;
  private final int length;
  private final Directions dire;

  /**
   * Initializes the ship to convert it
   *
   * @param myShip the ship being converted
   */
  public ShipAdapter(Ship myShip) {
    this.start = convertStart(myShip.getLocation());
    this.length = convertLength(myShip.getLocation());
    this.dire = convertDir(myShip.getLocation());
  }


  /**
   * Takes in the list of coordinates of a ship to
   * identify the direction it is in
   *
   * @param location list of coordinates of the placement of ship
   * @return the direction of the ship
   */
  private Directions convertDir(List<Coord> location) {
    if (location.get(0).getX() == location.get(1).getX()) {
      return Directions.VERTICAL;
    } else {
      return Directions.HORIZONTAL;
    }
  }

  /**
   * Takes in the list of coordinates of a ship to
   * identify the length of it
   *
   * @param location list of coordinates of the placement of ship
   * @return the length of the ship
   */
  private int convertLength(List<Coord> location) {
    return location.size();
  }

  /**
   * Takes in the list of coordinates of a ship to
   * identify the start of it
   *
   * @param location list of coordinates of the placement of ship
   * @return the start of the ship
   */
  private Coord convertStart(List<Coord> location) {
    return location.get(0);
  }

  /**
   * Returns the starting point of the ship
   *
   * @return the coordinate of where the ship starts
   */
  public Coord getStart() {
    return start;
  }

  /**
   * Returns the length of the ship
   *
   * @return the length of the ship
   */
  public int getLength() {
    return length;
  }

  /**
   * Returns the direction of the ship
   *
   * @return the direction of the ship
   */
  public Directions getDire() {
    return dire;
  }
}
