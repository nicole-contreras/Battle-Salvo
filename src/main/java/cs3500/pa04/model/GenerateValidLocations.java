package cs3500.pa04.model;

import cs3500.pa04.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates valid locations for the given ship type on the board.
 */
public class GenerateValidLocations {

  /**
   * Generates valid locations for the given ship type on the board.
   *
   * @param height     height of the board
   * @param width      width of the board
   * @param typeOfShip type of ship to generate
   * @param board      board to generate the ship on
   * @param rand       random number generator
   * @return list of coordinates of the ship
   */
  public static List<Coord> generateValidLocations(int height, int width,
                                                   ShipType typeOfShip, char[][] board,
                                                   Random rand) {

    int sizeOfShip = typeOfShip.getLength();
    List<Coord> validCoords = new ArrayList<>();

    while (validCoords.size() < sizeOfShip) {
      // Generate random coordinates
      int x = rand.nextInt(width);
      int y = rand.nextInt(height);

      // Check if the current position is empty
      if (board[y][x] == '-') {
        boolean canPlaceShip = true;
        List<Coord> shipCoords = new ArrayList<>();

        if (y + sizeOfShip <= height) {
          for (int i = 0; i < sizeOfShip; i++) {
            if (board[y + i][x] != '-') {
              canPlaceShip = false;
              break;
            }
            shipCoords.add(new Coord(x, y + i));
          }
        }

        if (!canPlaceShip && x + sizeOfShip <= width) {
          canPlaceShip = true;
          shipCoords.clear();
          for (int i = 0; i < sizeOfShip; i++) {
            if (board[y][x + i] != '-') {
              canPlaceShip = false;
              break;
            }
            shipCoords.add(new Coord(x + i, y));
          }
        }

        if (canPlaceShip) {
          validCoords.addAll(shipCoords);
        }
      }
    }
    return validCoords;
  }
}
