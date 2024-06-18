package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.ShipAdapterJson;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Directions;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests that the record FleetJSON
 */
public class FleetJsonTest {

  /**
   * Tests that the record properly serialized
   */
  @Test
  public void testSerialization() {
    List<ShipAdapterJson> testShips = new ArrayList<>();
    testShips.add(new ShipAdapterJson(new Coord(1, 1), 3, Directions.HORIZONTAL));
    testShips.add(new ShipAdapterJson(new Coord(2, 2), 2, Directions.VERTICAL));
    testShips.add(new ShipAdapterJson(new Coord(3, 3), 1, Directions.HORIZONTAL));

    FleetJson testFleet = new FleetJson(testShips);
    ObjectMapper mapper = new ObjectMapper();
    String jsonString;
    try {
      jsonString = mapper.writeValueAsString(testFleet);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    assertEquals(
        "{\"fleet\":[{\"coord\":{\"x\":1,\"y\":1},\"length\":3,"
            +
            "\"direction\":\"HORIZONTAL\"}"
            +
            ",{\"coord\":{\"x\":2,\"y\":2},\"length\":2,\"direction\":\"VERTICAL\"}"
            +
            ",{\"coord\":{\"x\":3,\"y\":3}"
            +
            ",\"length\":1,\"direction\":\"HORIZONTAL\"}]}",
        jsonString);
  }

  /**
   * Tests that the record properly deserialized
   */
  @Test
  public void testDeserialization() {
    String jsonString =
        "{\"fleet\":[{\"coord\":{\"x\":1,\"y\":1},\"length\":3,\"direction\":\"HORIZONTAL\"}"
            +
            ",{\"coord\":{\"x\":2,\"y\":2},\"length\":2,\"direction\":\"VERTICAL\"}"
            +
            ",{\"coord\":{\"x\":3,\"y\":3}"
            +
            ",\"length\":1,\"direction\":\"HORIZONTAL\"}]}";

    ObjectMapper mapper = new ObjectMapper();
    FleetJson json;
    try {
      json = mapper.readValue(jsonString, FleetJson.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    assertEquals(3, json.ships().size());
  }
}
