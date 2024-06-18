package cs3500.pa04.model;

import static cs3500.pa04.model.GenerateValidLocations.generateValidLocations;
import static cs3500.pa04.model.NonSunkenShips.updateNonSunkenShip;
import static cs3500.pa04.model.UpdateMisses.updateMisses;

import cs3500.pa04.GameResult;
import cs3500.pa04.ShipType;
import cs3500.pa04.view.ViewImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * represents an AI player
 */
public class AiPlayer extends AbstractPlayer {

  private final List<Coord> shotsAlreadyTaken;

  /**
   * Initializes the view and random object for an AIPlayer
   *
   * @param rand the random seed
   * @param view the view class
   */
  public AiPlayer(ViewImpl view, Random rand) {
    super(view, rand);
    shotsAlreadyTaken = new ArrayList<>();
    this.name = "AI Player";
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
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Coord> carrier;
    List<Coord> battleShip;
    List<Coord> destroyer;
    List<Coord> submarine;
    //initialises board
    board = new char[height][width];
    for (char[] chars : board) {
      Arrays.fill(chars, '-');
    }
    ArrayList<Ship> ships = new ArrayList<>();

    //randomly place ships
    for (int i = 0; i < specifications.get(ShipType.CARRIER); i++) {
      carrier =
          generateValidLocations(height, width,
              ShipType.CARRIER, board, rand);
      ships.add(new Ship(ShipType.CARRIER, carrier));
    }


    for (int i = 0; i < specifications.get(ShipType.BATTLESHIP); i++) {
      battleShip =
          generateValidLocations(height, width,
              ShipType.BATTLESHIP, board, rand);
      ships.add(new Ship(ShipType.BATTLESHIP, battleShip));

    }
    for (int i = 0; i < specifications.get(ShipType.DESTROYER); i++) {
      destroyer =
          generateValidLocations(height, width,
              ShipType.DESTROYER, board, rand);
      ships.add(new Ship(ShipType.DESTROYER, destroyer));
    }
    for (int i = 0; i < specifications.get(ShipType.SUBMARINE); i++) {
      submarine =
          generateValidLocations(height, width,
              ShipType.SUBMARINE, board, rand);
      ships.add(new Ship(ShipType.SUBMARINE, submarine));
    }
    view.setAiBoard(board);
    nonSunkenShips.addAll(ships);
    return ships;
  }

  /**
   * generates a random list of shots for the AI
   *
   * @return a list of shots
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> shots = new ArrayList<>();
    int numShots = nonSunkenShips.size();

    while (shots.size() < numShots) {
      int x = rand.nextInt(view.getWidth());
      int y = rand.nextInt(view.getHeight());
      Coord location = new Coord(x, y);

      boolean isDuplicate = false;
      for (Coord shot : shotsAlreadyTaken) {
        if (shot.getX() == location.getX() && shot.getY() == location.getY()) {
          isDuplicate = true;
          break;
        }
      }

      if (!isDuplicate) {
        shots.add(location);
        shotsAlreadyTaken.add(location);
      }
    }

    return shots;
  }

  /**
   * reports damage to the AI's board
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a list of hits
   */
  @Override
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
    updateMisses(listOfHits, opponentShotsOnBoard, board);
    return listOfHits;

  }

  /**
   * updates the AI's board with the shots that successfully hit the opponent's ships
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : shotsThatHitOpponentShips) {
      if (board[c.getY()][c.getX()] != 'M') {
        board[c.getY()][c.getX()] = 'H';
      }
    }
  }


  /**
   * ends the game
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  public void endGame(GameResult result, String reason) {
    try {
      view.endScreen(result);
    } catch (IOException e) {
      System.out.println("error in endGame");

    }
  }
}