package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.ShipAdapterJson;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Directions;
import org.junit.jupiter.api.Test;

/**
 * Tests the shipAdapterJSON record.
 */
public class ShipAdapterJsonTest {

  /**
   * Tests the serialisation of the shipAdapterJSON class.
   */
  @Test
  public void testSerialization() {
    ShipAdapterJson json = new ShipAdapterJson(new Coord(1, 1), 4, Directions.HORIZONTAL);
    ObjectMapper mapper = new ObjectMapper();
    String jsonString;
    try {
      jsonString = mapper.writeValueAsString(json);
    } catch (Exception e) {
      throw new IllegalArgumentException("Serialization failed");
    }
    assertEquals("{\"coord\":{\"x\":1,\"y\":1},\"length\":4,\"direction\":\"HORIZONTAL\"}",
        jsonString);
  }

  /**
   * Tests the deserialization of the shipAdapterJSON class.
   */
  @Test
  public void testDeserialisation() {
    String jsonString = "{\"coord\":{\"x\":1,\"y\":1},\"length\":4,\"direction\":\"VERTICAL\"}";
    ObjectMapper mapper = new ObjectMapper();
    ShipAdapterJson json;
    try {
      json = mapper.readValue(jsonString, ShipAdapterJson.class);
    } catch (Exception e) {
      throw new IllegalArgumentException("Deserialization failed");
    }
    assertEquals(new ShipAdapterJson(new Coord(1, 1), 4, Directions.VERTICAL), json);
  }

}
