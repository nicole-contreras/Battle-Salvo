package cs3500.pa04;

/**
 * Represents the result of a game.
 */
public enum GameResult {
  /**
   * Represents winning along with a label
   */
  WIN("you won!"),
  /**
   * Represents losing along with a label
   */
  LOSE("you lost :("),
  /**
   * Represents drawing along with a label
   */
  DRAW("its a tie!");

  private final String label;

  /**
   * setting the label of this enum
   *
   * @param label text associated with game result
   */
  GameResult(String label) {
    this.label = label;
  }

  /**
   * Returns the label of the result.
   *
   * @return the label of the result
   */
  public String getLabel() {
    return label;
  }
}
