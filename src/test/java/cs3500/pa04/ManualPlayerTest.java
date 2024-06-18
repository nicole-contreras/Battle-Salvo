package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Ship;
import cs3500.pa04.view.ViewImpl;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ManualPlayer class.
 */
public class ManualPlayerTest {

  ManualPlayer testPlayer;
  ViewImpl testView;

  /**
   * Sets up the test player and view.
   */
  @BeforeEach
  public void setUp() {
    String validInputs = "1 1\n1 1\n1 1\n1 1\n1 1\n1 1\n";
    StringBuilder testString1 = new StringBuilder();

    testView = new ViewImpl(new StringReader(validInputs), testString1, 6, 6);
    testPlayer = new ManualPlayer(testView, new Random(1));
  }

  /**
   * Tests the setup method.
   */
  @Test
  public void testSetup() {
    List<Ship> listOfShips = new ArrayList<>();
    assertEquals(0, listOfShips.size());
    try {
      listOfShips = testPlayer.setup(6, 6, testView.numberOfShips());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(4, listOfShips.size());
  }

  /**
   * Tests the takeShots method.
   */
  @Test
  public void testTakeShots() {
    try {
      testPlayer.setup(6, 6, testView.numberOfShips());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    List<Coord> expected = new ArrayList<>();
    expected.add(new Coord(1, 1));
    expected.add(new Coord(1, 1));
    expected.add(new Coord(1, 1));
    expected.add(new Coord(1, 1));
    expected.add(new Coord(1, 1));
    expected.add(new Coord(1, 1));
    int index = 0;
    List<Coord> actual = testPlayer.takeShots();

    for (Coord c : actual) {
      Coord tester = expected.get(index);
      assertEquals(tester.getX(), c.getX());
      assertEquals(tester.getY(), c.getY());
      index++;
    }

  }
}
