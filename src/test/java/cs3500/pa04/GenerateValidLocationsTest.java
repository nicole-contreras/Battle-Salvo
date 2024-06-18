package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GenerateValidLocations;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the GenerateValidLocations class.
 */
public class GenerateValidLocationsTest {

  Random rand;
  char[][] testBoard;
  char[][] testBoard2;

  /**
   * Sets up the test.
   */
  @BeforeEach
  public void setUp() {
    GenerateValidLocations tester = new GenerateValidLocations();
    rand = new Random(1);
    testBoard = new char[][] {
        {'-', '-', '-', '-', '-', '-'},
        {'-', '-', '-', '-', '-', '-'},
        {'-', '-', '-', '-', '-', '-'},
        {'-', '-', '-', '-', '-', '-'},
        {'-', '-', '-', '-', '-', '-'},
        {'-', '-', '-', '-', '-', '-'},
    };

    testBoard2 = new char[][] {
        {'B', 'B', 'B', '-', '-', '-'},
        {'B', 'B', 'B', 'B', 'B', 'B'},
        {'B', 'B', 'B', 'B', 'B', 'B'},
        {'B', 'B', 'B', 'B', 'B', 'B'},
        {'B', 'B', 'B', 'B', 'B', 'B'},
        {'B', 'B', 'B', 'B', 'B', 'B'},
    };

  }

  /**
   * test case 1
   */

  @Test
  public void testGenerateValidLocations() {
    List<Coord> expected = new ArrayList<>();
    expected.add(new Coord(2, 0));
    expected.add(new Coord(2, 1));
    expected.add(new Coord(2, 2));
    expected.add(new Coord(2, 3));
    expected.add(new Coord(2, 4));
    expected.add(new Coord(2, 5));

    int index = 0;
    List<Coord> actual =
        GenerateValidLocations.generateValidLocations(6, 6, ShipType.CARRIER, testBoard, rand);
    for (Coord c : actual) {
      assertEquals(expected.get(index).getX(), c.getX());
      assertEquals(expected.get(index).getY(), c.getY());
      index++;
    }
  }

  /**
   * test case 2
   */
  @Test
  public void testGenerate() {

    List<Coord> expected = new ArrayList<>();
    expected.add(new Coord(3, 0));
    expected.add(new Coord(4, 0));
    expected.add(new Coord(5, 0));

    int index = 0;
    List<Coord> actual =
        GenerateValidLocations.generateValidLocations(6, 6, ShipType.SUBMARINE, testBoard2, rand);
    for (Coord c : actual) {
      assertEquals(expected.get(index).getX(), c.getX());
      assertEquals(expected.get(index).getY(), c.getY());
      index++;
    }

  }
}
