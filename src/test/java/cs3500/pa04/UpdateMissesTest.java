package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.UpdateMisses;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


/**
 * Tests for the UpdateMisses class.
 */
public class UpdateMissesTest {

  /**
   * Tests the updateMisses method.
   */
  @Test
  void testUpdateMisses() {
    UpdateMisses testMisses = new UpdateMisses();

    List<Coord> successfulHits = new ArrayList<>();
    successfulHits.add(new Coord(1, 1));

    List<Coord> shotsTaken = new ArrayList<>();
    shotsTaken.add(new Coord(0, 0));
    shotsTaken.add(new Coord(1, 1));
    shotsTaken.add(new Coord(2, 2));
    char[][] board = {
        {'H', '-', '-'},
        {'-', '-', '-'},
        {'-', '-', '-'}
    };
    UpdateMisses.updateMisses(successfulHits, shotsTaken, board);

    char[][] expectedBoard = {
        {'H', '-', '-'},
        {'-', '-', '-'},
        {'-', '-', 'M'}
    };

    assertArrayEquals(expectedBoard, board);
  }
}
