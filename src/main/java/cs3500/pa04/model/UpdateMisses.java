package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class updates the misses on the board.
 */
public class UpdateMisses {
  /**
   * This method updates the misses on the board.
   *
   * @param successfulHits the list of successful hits
   * @param shotsTaken     the list of shots taken
   * @param board          the board
   */
  public static void updateMisses(List<Coord> successfulHits, List<Coord> shotsTaken,
                                  char[][] board) {

    List<Coord> misses = new ArrayList<>();
    for (Coord c : shotsTaken) {
      boolean isHit = false;
      for (Coord hits : successfulHits) {
        if (hits.getX() == c.getX() && hits.getY() == c.getY()) {
          isHit = true;
          break;
        }
      }
      if (!isHit) {
        misses.add(c);
      }
    }
    for (Coord c : misses) {
      if (board[c.getY()][c.getX()] != 'H') {
        board[c.getY()][c.getX()] = 'M';
      }
    }
  }
}
