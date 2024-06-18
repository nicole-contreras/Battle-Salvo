package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.Coord;
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
 * Tests for the AiPlayer class.
 */
public class AiPlayerTest {
  private AiPlayer testPlayer;
  private ViewImpl testView;

  /**
   * Sets up the test player and view.
   */
  @BeforeEach
  public void setUp() {
    String validInputs = "1 1\n1 1\n1 1\n1 1\n1 1\n1 1\n";
    StringBuilder testString1 = new StringBuilder();

    testView = new ViewImpl(new StringReader(validInputs), testString1, 6, 6);
    testPlayer = new AiPlayer(testView, new Random(2));
  }

  /**
   * Tests the setup method.
   */
  @Test
  public void testSetUp() {
    List<Ship> test = new ArrayList<>();
    assertEquals(0, test.size());
    try {
      test = testPlayer.setup(6, 6, testView.numberOfShips());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(4, test.size());
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
    expected.add(new Coord(1, 0));
    expected.add(new Coord(2, 2));
    expected.add(new Coord(4, 0));
    expected.add(new Coord(1, 3));

    int index = 0;
    List<Coord> actual = testPlayer.takeShots();

    for (Coord c : actual) {
      assertEquals(expected.get(index).getX(), c.getX());
      assertEquals(expected.get(index).getY(), c.getY());

      index++;
    }
  }
}
