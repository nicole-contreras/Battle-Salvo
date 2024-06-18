package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.SetUpJson;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Tests for the SetUpJson class.
 */
public class SetUpJsonTest {

  /**
   * Tests the serialisation of the SetUpJson class.
   */
  @Test
  public void testSerialisation() {
    Map<ShipType, Integer> testMap = new HashMap<>();
    testMap.put(ShipType.CARRIER, 1);
    testMap.put(ShipType.BATTLESHIP, 1);
    testMap.put(ShipType.SUBMARINE, 1);
    testMap.put(ShipType.DESTROYER, 1);

    SetUpJson testJson = new SetUpJson(6, 7, testMap);
    ObjectMapper mapper = new ObjectMapper();
    String json;
    try {
      json = mapper.writeValueAsString(testJson.width());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    // testing only the size as hashmap iterates through keys randomly
    assertEquals(
        "7",
        json);
    try {
      json = mapper.writeValueAsString(testJson.height());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    assertEquals(
        "6",
        json);
  }

  /**
   * Tests the deserialization of the SetUpJson class.
   */
  @Test
  public void testDeserialization() {
    String json =
        "{\"width\": 7, \"height\": 6, \"fleet-spec\": {\"CARRIER\": 1, \"BATTLESHIP\": 1,"
            + " \"SUBMARINE\": 1, "
            + "\"DESTROYER\": 2}}";
    ObjectMapper mapper = new ObjectMapper();
    SetUpJson testJson;
    try {
      testJson = mapper.readValue(json, SetUpJson.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    assertEquals(6, testJson.height());
    assertEquals(7, testJson.width());
    assertEquals(1, testJson.fleetSpec().get(ShipType.CARRIER));
    assertEquals(1, testJson.fleetSpec().get(ShipType.BATTLESHIP));
    assertEquals(1, testJson.fleetSpec().get(ShipType.SUBMARINE));
    assertEquals(2, testJson.fleetSpec().get(ShipType.DESTROYER));
  }

}
