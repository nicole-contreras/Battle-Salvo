package cs3500.pa04.view;

import cs3500.pa04.GameResult;
import cs3500.pa04.ShipType;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * represents the view implementation
 */
public class ViewImpl implements View {
  private final Appendable output;
  private final Scanner sc;
  private char[][] aiBoard;
  private char[][] manualBoard;
  private int height;
  private int width;

  /**
   * constructor for the view
   *
   * @param insert the input
   * @param output the output
   */
  public ViewImpl(Readable insert, Appendable output) {
    this.output = output;
    this.sc = new Scanner(insert);
  }

  /**
   * constructor for the view
   *
   * @param insert the input
   * @param output the output
   * @param height the height
   * @param width  the width
   */
  public ViewImpl(Readable insert, Appendable output, int height, int width) {
    this.output = output;
    this.sc = new Scanner(insert);
    this.height = height;
    this.width = width;
  }

  /**
   * outputs the opening message
   *
   * @throws IOException if an error occurs
   */
  public void openingMessage() throws IOException {
    boolean isValid = false;
    String tempHeight;
    String tempWidth;

    output.append("enter valid height and width\n");
    while (!isValid) {
      try {
        tempHeight = sc.next();
        tempWidth = sc.next();
        if ((Integer.parseInt(tempHeight) <= 15 && Integer.parseInt(tempHeight) >= 6)
            && (Integer.parseInt(tempWidth) <= 15 && Integer.parseInt(tempWidth) >= 6)) {
          height = Integer.parseInt(tempHeight);
          width = Integer.parseInt(tempWidth);
          isValid = true;
        } else {
          output.append("Remember, height and width must be between 6 and 15 inclusive\n");
        }
      } catch (NumberFormatException e) {
        output.append("must enter integer\n");
      }
    }
  }

  /**
   * takes in the number of ships from the user
   *
   * @return a map of the number of ships and how many there are
   * @throws IOException if an error occurs
   */
  public Map<ShipType, Integer> numberOfShips() throws IOException {
    boolean isValid = false;
    int smallerDimension = Math.min(height, width);
    int numberOfCarrier = 0;
    int numberOfBattleship = 0;
    int numberOfDestroyer = 0;
    int numberOfSubmarine = 0;

    output.append(
            "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
                + "Remember, your fleet may not exceed size ")
        .append(String.valueOf(smallerDimension))
        .append(".\n");
    while (!isValid) {
      String tempCarrier = sc.next();
      String tempBattleship = sc.next();
      String tempDestroyer = sc.next();
      String tempSubmarine = sc.next();
      try {
        numberOfCarrier = Integer.parseInt(tempCarrier);
        numberOfBattleship = Integer.parseInt(tempBattleship);
        numberOfDestroyer = Integer.parseInt(tempDestroyer);
        numberOfSubmarine = Integer.parseInt(tempSubmarine);
        int sumOfSizes =
            numberOfCarrier + numberOfBattleship + numberOfDestroyer + numberOfSubmarine;

        if (numberOfCarrier > 0 && numberOfBattleship > 0 && numberOfDestroyer > 0
            && numberOfSubmarine > 0 && sumOfSizes <= smallerDimension) {
          isValid = true;
        } else {
          output.append("invalid Integers\n");
        }
      } catch (NumberFormatException e) {
        output.append("inputs must enter integers\n");
      }
    }
    return Map.of(ShipType.CARRIER, numberOfCarrier, ShipType.BATTLESHIP, numberOfBattleship,
        ShipType.DESTROYER, numberOfDestroyer, ShipType.SUBMARINE, numberOfSubmarine);
  }

  /**
   * shows the manual board
   *
   * @throws IOException if an error occurs
   */
  public void showManualBoard() throws IOException {
    output.append("your board\n");

    for (char[] chars : manualBoard) {
      for (char c : chars) {
        output.append(String.valueOf(c)).append(" ");
      }
      output.append("\n");
    }
    output.append("-----------------------------------------\n");
  }

  /**
   * shows the AI board
   *
   * @throws IOException if an error occurs
   */
  public void showAiBoard() throws IOException {
    output.append("AI's board\n");
    for (char[] chars : aiBoard) {
      for (char c : chars) {
        output.append(String.valueOf(c)).append(" ");
      }
      output.append("\n");
    }
    output.append("-----------------------------------------\n");

  }

  /**
   * takes in the shots from the user
   *
   * @param notSunkShips the ships that are not sunk
   * @return a list of the shots
   * @throws IOException if an error occurs
   */
  public List<Coord> getShots(List<Ship> notSunkShips) throws IOException {
    List<Coord> shots = new ArrayList<>();
    boolean validCoordinate = false;

    while (!validCoordinate) {
      shots.clear(); // Clear shots from the previous iteration
      output.append("Please enter ").append(String.valueOf(notSunkShips.size()))
          .append(" shots: \n");
      output.append("-----------------------------------------\n");


      try {
        for (int i = 0; i < notSunkShips.size(); i++) {
          String tempX = sc.next();
          String tempY = sc.next();
          shots.add(new Coord(Integer.parseInt(tempX), Integer.parseInt(tempY)));
        }

        validCoordinate = true;

        for (Coord c : shots) {
          if (c.getX() < 0 || c.getX() >= width || c.getY() < 0 || c.getY() >= height) {
            validCoordinate = false;
            break;
          }
        }
      } catch (NumberFormatException e) {
        output.append("Error : inputs must enter integers\n");
      }
    }
    return shots;
  }

  /**
   * shows the user the end screen
   *
   * @param result the result of the game
   * @throws IOException if an error occurs
   */
  public void endScreen(GameResult result) throws IOException {
    if (result == GameResult.WIN) {
      output.append(GameResult.WIN.getLabel()).append("\n");
    } else if (result == GameResult.LOSE) {
      output.append(GameResult.LOSE.getLabel()).append("\n");
    } else {
      output.append(GameResult.DRAW.getLabel()).append("\n");
    }
  }

  /**
   * gets the height of the board
   *
   * @return the height of the board
   */
  public int getHeight() {
    return height;
  }

  /**
   * gets the width of the board
   *
   * @return the width of the board
   */
  public int getWidth() {
    return width;
  }

  /**
   * sets the manual board
   *
   * @param board the manual board
   */
  public void setManualBoard(char[][] board) {
    this.manualBoard = board;
  }

  /**
   * sets the AI board
   *
   * @param board the AI board
   */
  public void setAiBoard(char[][] board) {
    this.aiBoard = board;
  }


}