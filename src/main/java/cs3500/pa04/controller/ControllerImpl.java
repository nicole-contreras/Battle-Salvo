package cs3500.pa04.controller;

import cs3500.pa04.GameResult;
import cs3500.pa04.ShipType;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.view.ViewImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Represents a controller for the game of battleship.
 */
public class ControllerImpl implements Controller {

  private final Readable insert;
  private final Appendable output;
  private final Random rand;

  /**
   * Constructs a controller for the game of battleship.
   *
   * @param insert the input
   * @param output the output
   * @param rand   the random number generator
   */
  public ControllerImpl(Readable insert, Appendable output, Random rand) {
    this.insert = insert;
    this.output = output;
    this.rand = rand;
  }

  /**
   * Runs the game of battleship.
   *
   * @throws IOException if the appendable cannot be written to
   */
  @Override
  public void run() throws IOException {
    ViewImpl view = new ViewImpl(insert, output);
    Player manual = new ManualPlayer(view, rand);
    Player aiPlayer = new AiPlayer(view, rand);
    boolean gameOver = false;
    view.openingMessage();

    Map<ShipType, Integer> numberOfShips = view.numberOfShips();
    List<Ship> shipsOnManualBoard = manual.setup(view.getHeight(), view.getWidth(), numberOfShips);
    List<Ship> shipsOnAiBoard = aiPlayer.setup(view.getHeight(), view.getWidth(), numberOfShips);
    List<Coord> shipsHitByAi = new ArrayList<>();
    List<Coord> shipsHitByManualPlayer = new ArrayList<>();

    while (!gameOver) {
      view.showAiBoard();
      view.showManualBoard();

      List<Coord> manualShots = manual.takeShots();
      List<Coord> aiShots = aiPlayer.takeShots();

      shipsHitByManualPlayer.addAll(aiPlayer.reportDamage(manualShots));
      shipsHitByAi.addAll(manual.reportDamage(aiShots));

      boolean aiShipsSunk =
          new HashSet<>(shipsHitByManualPlayer).containsAll(getAllCoordinates(shipsOnAiBoard));
      boolean manualShipsSunk =
          new HashSet<>(shipsHitByAi).containsAll(getAllCoordinates(shipsOnManualBoard));

      if (aiShipsSunk && manualShipsSunk) {
        gameOver = true;
        manual.endGame(GameResult.DRAW, "Sunk each other's ships at the same time");
      } else if (aiShipsSunk) {
        gameOver = true;
        manual.endGame(GameResult.WIN, "Sunk all of your opponent's ships");
      } else if (manualShipsSunk) {
        gameOver = true;
        manual.endGame(GameResult.LOSE, "All of your ships have been sunk");
      }
    }
  }


  /**
   * Gets all the coordinates of the ships on the board.
   *
   * @param ships the ships on the board
   * @return a list of all the coordinates of the ships on the board
   */
  private List<Coord> getAllCoordinates(List<Ship> ships) {
    List<Coord> allCoordinates = new ArrayList<>();
    for (Ship ship : ships) {
      allCoordinates.addAll(ship.getLocation());
    }
    return allCoordinates;
  }

}
