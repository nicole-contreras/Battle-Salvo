package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.AbstractPlayer;
import cs3500.pa04.model.AiForServerPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests for the AiForServerPlayer class.
 */
public class AiForServerPlayerTest {

  /**
   * Tests the method setup by checking that sizes of the total ships on the
   * list equal the total ships given
   * in the hashmap
   */
  @Test
  public void testSetup() {
    AbstractPlayer player = new AiForServerPlayer(new Random(3));

    List<Ship> actual = player.setup(6, 6,
        new HashMap<>(Map.of(
            ShipType.CARRIER, 1, ShipType.BATTLESHIP, 1, ShipType.DESTROYER, 1, ShipType.SUBMARINE,
            1)));

    assertEquals(4, actual.size());

    List<Ship> actual1 = player.setup(6, 6,
        new HashMap<>(Map.of(
            ShipType.CARRIER, 0, ShipType.BATTLESHIP, 0, ShipType.DESTROYER, 0, ShipType.SUBMARINE,
            0)));

    assertEquals(0, actual1.size());

    List<Ship> actual2 = player.setup(6, 6,
        new HashMap<>(Map.of(
            ShipType.CARRIER, 2, ShipType.BATTLESHIP, 0, ShipType.DESTROYER, 1, ShipType.SUBMARINE,
            0)));

    assertEquals(3, actual2.size());
  }

  /**
   * Tests the takeShots method by checking that the number of shots are equal to the ships
   * on the board
   */
  @Test
  public void takeShots() {
    AbstractPlayer player = new AiForServerPlayer(new Random(3));

    // no ships on board
    List<Coord> expectedCoord = new ArrayList<>();
    assertEquals(expectedCoord, player.takeShots());


    player.setup(6, 6,
        new HashMap<>(Map.of(
            ShipType.CARRIER, 1, ShipType.BATTLESHIP, 1, ShipType.DESTROYER, 1, ShipType.SUBMARINE,
            1)));

    assertEquals(4, player.takeShots().size());
  }


  /**
   * Tests report damage by checking that the List of coords
   */
  @Test
  public void testReportDamage() {
    AbstractPlayer player = new AiForServerPlayer(new Random(3));
    player.setup(6, 6,
        new HashMap<>(Map.of(
            ShipType.CARRIER, 1, ShipType.BATTLESHIP,
            1, ShipType.DESTROYER, 1, ShipType.SUBMARINE,
            1)));

    List<Coord> expectedList = new ArrayList<>(List.of(new Coord(4, 4)));

    assertEquals(expectedList, player.reportDamage(new ArrayList<>(List.of(new Coord(0, 0),
        new Coord(0, 1), new Coord(4, 4)))));

    List<Coord> expectedList2 = new ArrayList<>();

    assertEquals(expectedList2, player.reportDamage(new ArrayList<>(List.of(new Coord(0, 0),
        new Coord(0, 1)))));
  }

}
