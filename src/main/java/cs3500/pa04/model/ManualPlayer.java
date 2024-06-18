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
 * Represents a human player in the game of battleship.
 */
public class ManualPlayer extends AbstractPlayer {


  /**
   * Initializes the view and random object for a player
   *
   * @param rand the random seed
   * @param view the view class
   */
  public ManualPlayer(ViewImpl view, Random rand) {
    super(view, rand);
    this.name = "Manual Player";
  }


  /**
   * Sets up the board for the player.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return a list of ships that have been placed on the board
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
    for (int i = 0; i < specifications.get(ShipType.CARRIER); i++) {
      carrier =
          generateValidLocations(height, width,
              ShipType.CARRIER, board, rand);
      shipsOnManualBoard.add(new Ship(ShipType.CARRIER, carrier));
      for (Coord c : carrier) {
        int x = c.getX();
        int y = c.getY();
        board[y][x] = ShipType.getRepresentation(ShipType.CARRIER);
      }
    }
    for (int i = 0; i < specifications.get(ShipType.BATTLESHIP); i++) {
      battleShip =
          generateValidLocations(height, width,
              ShipType.BATTLESHIP, board, rand);
      shipsOnManualBoard.add(new Ship(ShipType.BATTLESHIP, battleShip));
      for (Coord c : battleShip) {
        int x = c.getX();
        int y = c.getY();
        board[y][x] = ShipType.getRepresentation(ShipType.BATTLESHIP);
      }
    }
    for (int i = 0; i < specifications.get(ShipType.DESTROYER); i++) {
      destroyer =
          generateValidLocations(height, width,
              ShipType.DESTROYER, board, rand);
      shipsOnManualBoard.add(new Ship(ShipType.DESTROYER, destroyer));
      for (Coord c : destroyer) {
        int x = c.getX();
        int y = c.getY();
        board[y][x] = ShipType.getRepresentation(ShipType.DESTROYER);
      }
    }
    for (int i = 0; i < specifications.get(ShipType.SUBMARINE); i++) {
      submarine =
          generateValidLocations(height, width,
              ShipType.SUBMARINE, board, rand);
      shipsOnManualBoard.add(new Ship(ShipType.SUBMARINE, submarine));
      for (Coord c : submarine) {
        int x = c.getX();
        int y = c.getY();
        board[y][x] = ShipType.getRepresentation(ShipType.SUBMARINE);
      }
    }
    view.setManualBoard(board);
    nonSunkenShips.addAll(shipsOnManualBoard);
    return shipsOnManualBoard;
  }


  /**
   * Takes a shot at the opponent's board.
   *
   * @return a list of coordinates that the player wants to shoot at
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> listOfShots = new ArrayList<>();
    try {
      listOfShots = view.getShots(nonSunkenShips);
    } catch (IOException e) {
      System.out.println("error in takeShots");
    }
    return listOfShots;
  }

  /**
   * Reports the damage done by the opponent's shots on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a list of coordinates that hit the player's ships
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
   * Updates the board with the shots that successfully hit the players ships.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the players ships
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
   * notifies the player of the end of the game
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