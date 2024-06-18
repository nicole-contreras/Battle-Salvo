package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.view.ViewImpl;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the AbstractPlayer class.
 */
public class AbstractPlayerTest {
  private StringBuilder testString1;
  private ViewImpl testView;
  private Player testPlayer;
  private List<Coord> testShots;

  /**
   * Sets up the tests.
   */
  @BeforeEach
  public void setUp() {

    testShots = new ArrayList<>();
    testShots.add(new Coord(1, 1));
    testShots.add(new Coord(2, 2));
    testShots.add(new Coord(3, 3));
    testShots.add(new Coord(4, 4));

    String validInputs = "1 1 1 1\n";
    testString1 = new StringBuilder();


    testView = new ViewImpl(new StringReader(validInputs), testString1, 6, 6);
    testPlayer = new ManualPlayer(testView, new Random(1));
    try {
      testPlayer.setup(6, 6, testView.numberOfShips());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Tests the reportDamage method.
   */
  @Test
  public void testReportDamage() {
    List<Coord> expected = new ArrayList<>();
    expected.add(new Coord(2, 2));
    expected.add(new Coord(3, 3));

    List<Coord> actual = testPlayer.reportDamage(testShots);

    int index = 0;
    for (Coord c : actual) {
      Coord tester = expected.get(index);
      assertEquals(tester.getX(), c.getX());
      assertEquals(tester.getY(), c.getY());
      index++;
    }
  }

  /**
   * Tests the successfulHits method.
   */
  @Test
  public void testSuccessfulHits() {

    try {
      testView.showManualBoard();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String expectedBoard = """
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
        """;
    assertEquals(expectedBoard, testString1.toString());
    testString1.delete(0, testString1.length());
    testPlayer.reportDamage(testShots);

    try {
      testView.showManualBoard();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals("""
        your board
        S - C B - -\s
        S M C B - -\s
        S D H B - -\s
        - D C H - -\s
        - D C B M -\s
        - D C - - -\s
        -----------------------------------------
        """, testString1.toString());
  }

  /**
   * Tests the endGame method.
   */
  @Test
  public void testEndGame() {
    testString1.delete(0, testString1.length());

    testPlayer.endGame(GameResult.LOSE, "you lost");
    assertEquals("you lost :(\n", testString1.toString());
    testString1.delete(0, testString1.length());

    testPlayer.endGame(GameResult.WIN, "you won");
    assertEquals("you won!\n", testString1.toString());
    testString1.delete(0, testString1.length());

    testPlayer.endGame(GameResult.DRAW, "you tied");
    assertEquals("its a tie!\n", testString1.toString());
    testString1.delete(0, testString1.length());
  }

}
