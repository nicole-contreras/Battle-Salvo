package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Ship;
import cs3500.pa04.view.ViewImpl;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests for the View class.
 */
public class ViewTest {

  /**
   * Tests the openingMessage method.
   */
  @Test
  public void testOpeningMessage() {
    String validInputs = "hello 6\n6 7\n";
    String heightTooSmall = "2 6\n6 7\n";
    String widthTooSmall = "6 2\n6 7\n";
    String heightTooBig = "20 6\n6 7\n";
    String widthTooBig = "6 20\n6 7\n";

    StringBuilder testString1 = new StringBuilder();
    StringBuilder testString2 = new StringBuilder();
    StringBuilder testString3 = new StringBuilder();
    StringBuilder testString4 = new StringBuilder();
    StringBuilder testString5 = new StringBuilder();

    ViewImpl testView1 = new ViewImpl(new StringReader(validInputs), testString1);
    ViewImpl testView2 = new ViewImpl(new StringReader(heightTooSmall), testString2);
    ViewImpl testView3 = new ViewImpl(new StringReader(widthTooSmall), testString3);
    ViewImpl testView4 = new ViewImpl(new StringReader(heightTooBig), testString4);
    ViewImpl testView5 = new ViewImpl(new StringReader(widthTooBig), testString5);


    try {
      testView1.openingMessage();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


    try {
      testView2.openingMessage();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    try {
      testView3.openingMessage();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    try {
      testView4.openingMessage();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    try {
      testView5.openingMessage();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    assertEquals("""
        enter valid height and width
        must enter integer
        """, testString1.toString());
    assertEquals("""
            enter valid height and width
            Remember, height and width must be between 6 and 15 inclusive
            """,
        testString2.toString());
    assertEquals("""
            enter valid height and width
            Remember, height and width must be between 6 and 15 inclusive
            """,
        testString3.toString());
    assertEquals("""
            enter valid height and width
            Remember, height and width must be between 6 and 15 inclusive
            """,
        testString4.toString());
    assertEquals("""
            enter valid height and width
            Remember, height and width must be between 6 and 15 inclusive
            """,
        testString5.toString());
  }

  /**
   * Tests the numberOfShips method.
   */
  @Test
  public void testNumberOfShips() {
    String validInputs = "1 1 1 hello\n1 1 1 1\n";
    String invalidCarrier = "0 1 1 1\n1 1 1 1\n";
    String invalidBattleship = "1 0 1 1\n1 1 1 1\n";
    String invalidSubmarine = "1 1 1 0\n1 1 0 1\n1 1 1 1\n";
    String overallInvalid = "3 3 3 3\n1 1 1 1\n";


    StringBuilder testString1 = new StringBuilder();
    StringBuilder testString2 = new StringBuilder();
    StringBuilder testString3 = new StringBuilder();
    StringBuilder testString4 = new StringBuilder();
    StringBuilder testString5 = new StringBuilder();


    ViewImpl testView1 = new ViewImpl(new StringReader(validInputs), testString1, 6, 7);
    ViewImpl testView2 = new ViewImpl(new StringReader(invalidCarrier), testString2, 6, 7);
    ViewImpl testView3 = new ViewImpl(new StringReader(invalidBattleship), testString3, 6, 7);
    ViewImpl testView4 = new ViewImpl(new StringReader(invalidSubmarine), testString4, 6, 7);
    ViewImpl testView5 = new ViewImpl(new StringReader(overallInvalid), testString5, 7, 7);

    try {
      testView1.numberOfShips();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      testView2.numberOfShips();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    try {
      testView3.numberOfShips();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    try {
      testView4.numberOfShips();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    try {
      testView5.numberOfShips();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    assertEquals(
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 6.
            inputs must enter integers
            """, testString1.toString());
    assertEquals(
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 6.
            invalid Integers
            """, testString2.toString());
    assertEquals(
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 6.
            invalid Integers
            """, testString3.toString());
    assertEquals(
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 6.
            invalid Integers
            invalid Integers
            """, testString4.toString());

    assertEquals(
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 7.
            invalid Integers
            """, testString5.toString());

  }

  /**
   * Tests the showManualBoard method.
   */
  @Test
  public void testShowManualBoard() {
    String validInputs = "1 1 1 1\n";
    StringBuilder testString1 = new StringBuilder();

    ViewImpl testView = new ViewImpl(new StringReader(validInputs), testString1, 6, 6);

    ManualPlayer testPlayer = new ManualPlayer(testView, new Random(1));
    try {
      testPlayer.setup(testView.getHeight(), testView.getWidth(), testView.numberOfShips());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      testView.showManualBoard();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals(
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 6.
            your board
            S - C B - -\s
            S - C B - -\s
            S D C B - -\s
            - D C B - -\s
            - D C B - -\s
            - D C - - -\s
            -----------------------------------------
            """, testString1.toString());
  }

  /**
   * Tests the showAiBoard method.
   */
  @Test
  public void testShowAiBoard() {
    String validInputs = "1 1 1 1\n";
    StringBuilder testString1 = new StringBuilder();

    ViewImpl testView = new ViewImpl(new StringReader(validInputs), testString1, 6, 6);

    AiPlayer testPlayer = new AiPlayer(testView, new Random(1));
    try {
      testPlayer.setup(testView.getHeight(), testView.getWidth(), testView.numberOfShips());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      testView.showAiBoard();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals(
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember, your fleet may not exceed size 6.
            AI's board
            - - - - - -\s
            - - - - - -\s
            - - - - - -\s
            - - - - - -\s
            - - - - - -\s
            - - - - - -\s
            -----------------------------------------
            """, testString1.toString());
  }

  /**
   * Tests the get shots method
   */
  @Test
  public void testGetShots() {
    List<Ship> testFleet = new ArrayList<>();
    List<Coord> testCoords = new ArrayList<>();
    testCoords.add(new Coord(0, 0));
    testCoords.add(new Coord(0, 1));

    testFleet.add(new Ship(ShipType.DESTROYER, testCoords));
    testFleet.add(new Ship(ShipType.BATTLESHIP, testCoords));

    String validInputs = "1 1\n1 hello\n1 1\n1 1\n";
    String invalidInputs = "1 20\n-1 1\n20 1\n1 -2\n6 1\n1 6\n1 1\n1 1\n";

    StringBuilder testString1 = new StringBuilder();
    StringBuilder testString2 = new StringBuilder();

    ViewImpl testView = new ViewImpl(new StringReader(validInputs), testString1, 6, 6);
    ViewImpl testView2 = new ViewImpl(new StringReader(invalidInputs), testString2, 6, 6);

    try {
      testView.getShots(testFleet);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      testView2.getShots(testFleet);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals("""
        Please enter 2 shots:\s
        -----------------------------------------
        Error : inputs must enter integers
        Please enter 2 shots:\s
        -----------------------------------------
        """, testString1.toString());
    assertEquals("""
        Please enter 2 shots:\s
        -----------------------------------------
        Please enter 2 shots:\s
        -----------------------------------------
        Please enter 2 shots:\s
        -----------------------------------------
        Please enter 2 shots:\s
        -----------------------------------------
        """, testString2.toString());
  }

  /**
   * Tests the end screen method
   */
  @Test
  public void testEndScreen() {
    String validInputs = "1 1 1 1\n";
    StringBuilder testString1 = new StringBuilder();
    StringBuilder testString2 = new StringBuilder();
    StringBuilder testString3 = new StringBuilder();

    ViewImpl testView = new ViewImpl(new StringReader(validInputs), testString1, 6, 6);
    ViewImpl testView2 = new ViewImpl(new StringReader(validInputs), testString2, 6, 6);
    ViewImpl testView3 = new ViewImpl(new StringReader(validInputs), testString3, 6, 6);

    try {
      testView.endScreen(GameResult.WIN);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      testView2.endScreen(GameResult.LOSE);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try {
      testView3.endScreen(GameResult.DRAW);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals("you won!\n", testString1.toString());
    assertEquals("you lost :(\n", testString2.toString());
    assertEquals("its a tie!\n", testString3.toString());

  }

}
