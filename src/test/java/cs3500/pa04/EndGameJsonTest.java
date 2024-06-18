package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.EndGameJson;
import org.junit.jupiter.api.Test;

/**
 * Tests that the record EndGameJSON
 */
public class EndGameJsonTest {

  /**
   * Tests that the record properly serialized
   */
  @Test
  public void testSerialization() {

    GameResult result = GameResult.WIN;
    String reason = "You sunk all of the opponent's ships!";
    EndGameJson endGameJson = new EndGameJson(result, reason);
    ObjectMapper mapper = new ObjectMapper();
    String jsonString;
    try {
      jsonString = mapper.writeValueAsString(endGameJson);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    assertEquals("{\"result\":\"WIN\",\"reason\":\"You sunk all of the opponent's ships!\"}",
        jsonString);

    //for lose
    GameResult result3 = GameResult.DRAW;
    String reason3 = "sunk all of each other's ship";
    EndGameJson endGameJson3 = new EndGameJson(result3, reason3);
    try {
      jsonString = mapper.writeValueAsString(endGameJson3);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    assertEquals("{\"result\":\"DRAW\",\"reason\":\"sunk all of each other's ship\"}",
        jsonString);

    GameResult result2 = GameResult.LOSE;
    String reason2 = "the opponent sunk all of your ships";
    EndGameJson endGameJson2 = new EndGameJson(result2, reason2);
    try {
      jsonString = mapper.writeValueAsString(endGameJson2);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    assertEquals("{\"result\":\"LOSE\",\"reason\":\"the opponent sunk all of your ships\"}",
        jsonString);

  }

  /**
   * Tests that the record properly deserialized
   */
  @Test
  public void testDeserialisation() {
    String jsonString = "{\"result\":\"WIN\",\"reason\":\"You sunk all of the opponent's ships!\"}";

    ObjectMapper mapper = new ObjectMapper();
    EndGameJson json;
    try {
      json = mapper.readValue(jsonString, EndGameJson.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    assertEquals(GameResult.WIN, json.result());
    assertEquals("You sunk all of the opponent's ships!", json.reason());

    //for lose
    String jsonString3 = "{\"result\":\"DRAW\",\"reason\":\"sunk all of each other's ship\"}";
    try {
      json = mapper.readValue(jsonString3, EndGameJson.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    assertEquals(GameResult.DRAW, json.result());
    assertEquals("sunk all of each other's ship", json.reason());

    String jsonString2 = "{\"result\":\"LOSE\",\"reason\":\"the opponent sunk all of your ships\"}";
    try {
      json = mapper.readValue(jsonString2, EndGameJson.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    assertEquals(GameResult.LOSE, json.result());
    assertEquals("the opponent sunk all of your ships", json.reason());
  }
}
