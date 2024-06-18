package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.MessageJson;
import org.junit.jupiter.api.Test;

/**
 * Tests the MessageJSON
 */
public class MessageJsonTest {

  /**
   * Tests that the record properly serialized
   */
  @Test
  public void testSerialization() {
    ObjectMapper mapper = new ObjectMapper();
    String arguements = """
        {
            "name" : "white-hall-escapist",
            "game-type" : "SINGLE"
          }""";
    JsonNode json = mapper.convertValue(arguements, JsonNode.class);
    MessageJson message = new MessageJson("create-game", json);
    String jsonString;
    try {
      jsonString = mapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    assertEquals(
        "{\"method-name\":\"create-game\",\"arguments\":\"{\\n   "
            + " \\\"name\\\" : \\\"white-hall-escapist\\\",\\n    "
            + "\\\"game-type\\\" : \\\"SINGLE\\\"\\n  }\"}",
        jsonString);

  }

  /**
   * Tests that the record properly deserialized
   */
  @Test
  public void testDeserialization() {
    String jsonString =
        "{\"method-name\":\"create-game\",\"arguments\":\"{\\n "
            + "   \\\"name\\\" : \\\"white-hall-escapist\\\",\\n "
            + "   \\\"game-type\\\" : \\\"SINGLE\\\"\\n  }\"}";

    ObjectMapper mapper = new ObjectMapper();
    MessageJson json;
    try {
      json = mapper.readValue(jsonString, MessageJson.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    assertEquals("create-game", json.messageName());
    assertEquals("\"{\\n    \\\"name\\\" : \\\"white-hall-escapist\\\",\\n"
        + "    \\\"game-type\\\" : \\\"SINGLE\\\"\\n  }\"", json.arguments().toString());

  }
}
