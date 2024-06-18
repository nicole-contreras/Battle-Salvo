package cs3500.pa04.view;

import cs3500.pa04.GameResult;
import cs3500.pa04.ShipType;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Represents the view for the game.
 */
public interface View {
  /**
   * Displays the opening message for the game.
   *
   * @throws IOException if the appendable object cannot be written to.
   */
  public void openingMessage() throws IOException;

  /**
   * asks for number of ships for each type.
   *
   * @return a map of the number of ships for each type.
   * @throws IOException if the appendable object cannot be written to.
   */
  public Map<ShipType, Integer> numberOfShips() throws IOException;

  /**
   * Displays the players board for the game.
   *
   * @throws IOException if the appendable object cannot be written to.
   */
  public void showManualBoard() throws IOException;

  /**
   * Displays the AI's board for the game.
   *
   * @throws IOException if the appendable object cannot be written to.
   */
  public void showAiBoard() throws IOException;

  /**
   * take shots from the user
   *
   * @param notSunkShips the list of ships that are not sunk.
   * @return the list of shots that the user wants to take.
   * @throws IOException if the appendable object cannot be written to.
   */
  public List<Coord> getShots(List<Ship> notSunkShips) throws IOException;

  /**
   * Displays the result of the game.
   *
   * @param result the result of the game
   * @throws IOException if the appendable object cannot be written to.
   */
  public void endScreen(GameResult result) throws IOException;


}
