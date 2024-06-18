package cs3500.pa04.controller;


import java.io.IOException;

/**
 * Represents a controller for the game.
 */
public interface Controller {
  /**
   * Runs the game.
   *
   * @throws IOException if the input or output is invalid
   */
  public void run() throws IOException;
}
