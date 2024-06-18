package cs3500.pa04.model;

import static cs3500.pa04.model.NonSunkenShips.updateNonSunkenShip;

import cs3500.pa04.GameResult;
import cs3500.pa04.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an AI for the server player.
 */
public class AiForServerPlayer extends AbstractPlayer {

  private final List<Coord> coordsAvailable = new ArrayList<>();

  private final Map<ShipType, Integer> shipLength = Map.of(
      ShipType.CARRIER, 6,
      ShipType.BATTLESHIP, 5,
      ShipType.DESTROYER, 4,
      ShipType.SUBMARINE, 3);

  /**
   * Initializes the random object
   *
   * @param rand random seed
   */
  public AiForServerPlayer(Random rand) {
    super(null, rand);
    this.rand = rand;
    this.name = "white-hall-escapist";
  }

  /**
   * sets up the board
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return a list of ships
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> shipsOnBoard = new ArrayList<>();
    List<ShipType> shipModels = new ArrayList<>(Arrays.asList(
        ShipType.CARRIER, ShipType.BATTLESHIP, ShipType.DESTROYER, ShipType.SUBMARINE));
    List<Coord> coordsForSetUp = new ArrayList<>();

    // coords of all the empty spots in the board
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        coordsForSetUp.add(new Coord(j, i));
        coordsAvailable.add(new Coord(j, i));
      }
    }

    // iterates through ship
    for (ShipType modelShip : shipModels) {
      for (int i = 0; i < specifications.get(modelShip); i++) {
        List<Coord> tempCoordList = placeShip(modelShip, coordsForSetUp, height, width);
        for (Coord posn : tempCoordList) {
          coordsForSetUp.remove(posn);
        }
        shipsOnBoard.add(new Ship(modelShip, new ArrayList<>(tempCoordList)));
      }
    }

    nonSunkenShips.addAll(shipsOnBoard);
    return shipsOnBoard;
  }

  /**
   * places the ships on a board
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param modelShip      the model of the boat wanting to place
   * @param coordsForSetUp the list of coords open for placement
   *
   * @return a list coordinates of where the ship is placed
   */
  private List<Coord> placeShip(ShipType modelShip, List<Coord> coordsForSetUp, int height,
                                int width) {
    List<Coord> tempCoordList = new ArrayList<>();
    int length = shipLength.get(modelShip);

    // random coordinate
    int randX = rand.nextInt(height);
    int randY = rand.nextInt(width);

    // pick direction at random
    boolean isHorizontal = rand.nextBoolean();

    while (tempCoordList.isEmpty()) {
      for (int j = 0; j < length; j++) {
        // if its horizontal add to x value, if not add to y value
        Coord coord = isHorizontal ? new Coord(randX + j, randY) : new Coord(randX, randY + j);
        if (coordsForSetUp.contains(coord)) {
          tempCoordList.add(coord);
        } else {
          tempCoordList.clear();
          randX = rand.nextInt(width);
          randY = rand.nextInt(height);
          isHorizontal = !isHorizontal;
          break;
        }
      }
    }
    return tempCoordList;
  }

  /**
   * generates a random list of shots for the AI
   *
   * @return a list of shots
   */
  public List<Coord> takeShots() {
    List<Coord> shots = new ArrayList<>();
    int numShots = Math.min(nonSunkenShips.size(), coordsAvailable.size());

    while (shots.size() < numShots) {
      int randIndx = rand.nextInt(coordsAvailable.size());
      Coord location = coordsAvailable.remove(randIndx);
      shots.add(location);
    }
    return shots;
  }

  /**
   * Reports damage to the player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a list of coordinates of the shots that hit the player's ships
   */
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> listOfHits = new ArrayList<>();

    for (Ship s : nonSunkenShips) {
      List<Coord> location = s.getLocation();
      for (Coord shot : opponentShotsOnBoard) {
        for (Coord c : location) {
          if (c.getX() == shot.getX() && c.getY() == shot.getY()) {
            listOfHits.add(c);
            s.removeCoord(c.getX(), c.getY());
            break;
          }
        }
      }
    }
    updateNonSunkenShip(nonSunkenShips);
    successfulHits(listOfHits);
    return listOfHits;
  }

  /**
   * informs the user of the result of the game
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }

  /**
   * informs the user of the successful hits
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
  }

}
