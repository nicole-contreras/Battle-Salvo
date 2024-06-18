package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.model.Coord;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests the record CoordinatesJSON
 */
public class CoordinatesJsonTest {

  /**
   * Tests that the record properly serialized
   */
  @Test
  public void testSerialization() {
    //non empty array
    List<Coord> coords = new ArrayList<>();
    coords.add(new Coord(1, 2));
    coords.add(new Coord(3, 4));
    CoordinatesJson json = new CoordinatesJson(coords);

    ObjectMapper mapper = new ObjectMapper();
    String jsonString;
    try {
      jsonString = mapper.writeValueAsString(json);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    String expectedJsonString = "{\"coordinates\":[{\"x\":1,\"y\":2},{\"x\":3,\"y\":4}]}";
    assertEquals(expectedJsonString, jsonString);

    //test for empty array
    List<Coord> coords2 = new ArrayList<>();
    CoordinatesJson json2 = new CoordinatesJson(coords2);
    String jsonString2;
    try {
      jsonString2 = mapper.writeValueAsString(json2);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    String expectedJsonString2 = "{\"coordinates\":[]}";
    assertEquals(expectedJsonString2, jsonString2);
  }

  /**
   * Tests the deserialization
   */
  @Test
  public void testDeserialization() {
    String jsonString = "{\"coordinates\":[{\"x\":1,\"y\":2},{\"x\":3,\"y\":4}]}";

    ObjectMapper mapper = new ObjectMapper();
    CoordinatesJson json;
    try {
      json = mapper.readValue(jsonString, CoordinatesJson.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    List<Coord> expectedCoords = new ArrayList<>();
    expectedCoords.add(new Coord(1, 2));
    expectedCoords.add(new Coord(3, 4));

    assertEquals(expectedCoords, json.coords());

    //test for empty array
    String jsonString2 = "{\"coordinates\":[]}";
    CoordinatesJson json2;
    try {
      json2 = mapper.readValue(jsonString2, CoordinatesJson.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    List<Coord> coords2 = new ArrayList<>();

    assertEquals(coords2, json2.coords());
  }

}
