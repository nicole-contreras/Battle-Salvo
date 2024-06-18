package cs3500.pa04;

/**
 * Represents the type of ship.
 */
public enum ShipType {
  /**
   * Carrier with length of 6
   */
  CARRIER(6),
  /**
   * Battleship with length of 5
   */
  BATTLESHIP(5),
  /**
   * Destroyer with length of 4
   */
  DESTROYER(4),
  /**
   * Submarine with length of 3
   */
  SUBMARINE(3);

  private final int length;

  /**
   * returns the length of the ship
   *
   * @param length integer length of ship
   */
  ShipType(int length) {
    this.length = length;
  }

  /**
   * Gets the representation of the ship.
   *
   * @param type the type of ship
   * @return the representation of the ship
   */
  public static char getRepresentation(ShipType type) {
    if (type == CARRIER) {
      return 'C';
    } else if (type == BATTLESHIP) {
      return 'B';
    } else if (type == DESTROYER) {
      return 'D';
    } else {
      return 'S';
    }
  }

  /**
   * Gets the length of the ship.
   *
   * @return the length of the ship
   */
  public int getLength() {
    return length;
  }
}
