package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * Represents a coordinate on the board
 */
public class Coord {
  private final int coordX;
  private final int coordY;


  /**
   * Serializes the Coord class
   *
   * @param x the x-coordinate
   * @param y the y-coordinate
   */
  @JsonCreator
  public Coord(@JsonProperty("x") int x,
               @JsonProperty("y") int y) {
    this.coordX = x;
    this.coordY = y;
  }

  /**
   * overrides the equals method to determine whether coordinates are equal
   * based on the fields
   *
   * @return whether the coordinates have the same x and y value
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return coordX == coord.coordX && coordY == coord.coordY;
  }

  /**
   * overrides hashcode method
   *
   * @return the hashcode produced
   */
  @Override
  public int hashCode() {
    return Objects.hash(coordX, coordY);
  }

  /**
   * Returns the x coordinate
   *
   * @return the x coordinate
   */
  public int getX() {
    return coordX;
  }

  /**
   * Returns the y coordinate
   *
   * @return the y coordinate
   */
  public int getY() {
    return coordY;
  }
}
