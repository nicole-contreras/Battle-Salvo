package cs3500.pa04.model;

import cs3500.pa04.GameResult;
import cs3500.pa04.ShipType;
import cs3500.pa04.view.ViewImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an abstract player.
 */
public abstract class AbstractPlayer implements Player {
  /**
   * Represents a 2D array for a board
   */
  protected char[][] board;
  /**
   * Represents the view class
   */
  protected ViewImpl view;
  /**
   * Represents the ships alive of the user
   */
  protected List<Ship> shipsOnManualBoard = new ArrayList<>();
  /**
   * Represents the ships alive of the ai player
   */
  protected List<Ship> nonSunkenShips = new ArrayList<>();
  /**
   * Represents the random object given
   */
  protected Random rand;
  /**
   * Represents the name given
   */
  protected String name;

  /**
   * Instantiates the view and random object
   *
   * @param view the view class
   * @param rand the random object
   */
  public AbstractPlayer(ViewImpl view, Random rand) {
    this.view = view;
    this.rand = rand;
  }

  /**
   * gets the name of the player.
   *
   * @return the name of the player
   */
  @Override
  public String name() {
    return "white-hall-escapist";
  }

  /**
   * Sets up the board for the player.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return a list of ships that are on the board
   */
  @Override
  public abstract List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications);


  /**
   * Takes shots on the opponent's board.
   *
   * @return a list of coordinates of the shots
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Reports damage to the player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a list of coordinates of the shots that hit the player's ships
   */
  @Override
  public abstract List<Coord> reportDamage(List<Coord> opponentShotsOnBoard);


  /**
   * Updates the board with the shots that successfully hit the opponent's ships.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public abstract void successfulHits(List<Coord> shotsThatHitOpponentShips);

  /**
   * informs the user of the result of the game
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  public abstract void endGame(GameResult result, String reason);

}
