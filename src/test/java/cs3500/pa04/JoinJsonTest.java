package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.model.GameType;
import org.junit.jupiter.api.Test;

/**
 * Tests the record JoinJSON
 */
public class JoinJsonTest {

  /**
   * Tests that the record properly serialized
   */
  @Test
  public void testSerialisation() {
    JoinJson json = new JoinJson("name", GameType.SINGLE);
    ObjectMapper mapper = new ObjectMapper();
    String jsonString;
    try {
      jsonString = mapper.writeValueAsString(json);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    assertEquals("{\"name\":\"name\",\"game-type\":\"SINGLE\"}", jsonString);
  }

  /**
   * Tests that the record properly deserialized
   */
  @Test
  public void testDeserialization() {
    String jsonString = "{\"name\":\"name\",\"game-type\":\"MULTI\"}";
    ObjectMapper mapper = new ObjectMapper();
    JoinJson json;
    try {
      json = mapper.readValue(jsonString, JoinJson.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    
    assertEquals("name", json.name());
    assertEquals(GameType.MULTI, json.gameType());
  }
}
