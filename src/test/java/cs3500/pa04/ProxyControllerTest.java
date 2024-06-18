package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetUpJson;
import cs3500.pa04.model.AiForServerPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Player;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyControllerTest {

  private final ObjectMapper mapper = new ObjectMapper();
  private ByteArrayOutputStream testLog;
  private ProxyController controller;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Check that the server returns the valid json when join is sent
   */
  @Test
  public void testJoin() {
    // Prepare sample message
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode emptyJsonNode = objectMapper.createObjectNode();

    MessageJson joinServerRequest = new MessageJson("join", emptyJsonNode);

    JsonNode sampleMessage = this.mapper.convertValue(joinServerRequest, JsonNode.class);
    EndGameJson endGameArgs =
        new EndGameJson(GameResult.WIN, "Player 1 sank all of Player 2's ships");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("end-game", endGameArgsNode);

    JsonNode endGameMsg = this.mapper.convertValue(endGameServerRequest, JsonNode.class);

    // Create the client with all necessary messages
    Mocket socket =
        new Mocket(this.testLog, List.of(sampleMessage.toString(), endGameMsg.toString()));

    // Create a Dealer
    try {
      Player player = new AiForServerPlayer(new Random());
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();

    String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":\"white-hall-escapist\","
        + "\"game-type\":\"SINGLE\"}}" + System.lineSeparator()
        + "{\"method-name\":\"end-game\",\"arguments\":{}}" + System.lineSeparator();

    assertEquals(expected, logToString());
  }

  /**
   * Check that the server returns the valid json when setup is sent
   */
  @Test
  public void testTakeShots() {

    // Prepare sample message
    ObjectMapper objectMapper = new ObjectMapper();

    JsonNode emptyJsonNode = objectMapper.createObjectNode();

    MessageJson takeShotsServerRequest = new MessageJson("take-shots", emptyJsonNode);

    JsonNode sampleMessage = this.mapper.convertValue(takeShotsServerRequest, JsonNode.class);

    EndGameJson endGameArgs =
        new EndGameJson(GameResult.WIN, "Player 1 sank all of Player 2's ships");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("end-game", endGameArgsNode);

    JsonNode endGameMsg = this.mapper.convertValue(endGameServerRequest, JsonNode.class);

    // Create the client with all necessary messages
    Mocket socket =
        new Mocket(this.testLog, List.of(sampleMessage.toString(), endGameMsg.toString()));

    // Create a Dealer
    try {
      Player player = new AiForServerPlayer(new Random(3));
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();


    // testing where there's no ships
    String expected = "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[]}}"
        + System.lineSeparator()
        + "{\"method-name\":\"end-game\",\"arguments\":{}}" + System.lineSeparator();

    assertEquals(expected, logToString());

    // testing where there's 4 ships
    setup();

    Map<ShipType, Integer> shipCounts = Map.of(
        ShipType.CARRIER, 1,
        ShipType.BATTLESHIP, 1,
        ShipType.DESTROYER, 1,
        ShipType.SUBMARINE, 1
    );

    SetUpJson setUpJson = new SetUpJson(6, 6, shipCounts);
    JsonNode setup = this.mapper.convertValue(setUpJson, JsonNode.class);

    MessageJson setupServerRequest = new MessageJson("setup", setup);

    JsonNode sampleMessage2 = this.mapper.convertValue(setupServerRequest, JsonNode.class);
    // Create the client with all necessary messages

    Mocket socket2 =
        new Mocket(this.testLog, List.of(sampleMessage.toString(), sampleMessage2.toString(),
            endGameMsg.toString()));

    // Create a Dealer
    try {
      Player player = new AiForServerPlayer(new Random(3));
      this.controller = new ProxyController(socket2, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();

    String expected2 = "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[]}}"
        + System.lineSeparator()
        + "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\"x\":4,\"y\":0},\""
        + "length\":6,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":2,\"y\":1},\"length\":5,\""
        + "direction\":\"VERTICAL\"},{\"coord\":{\"x\":5,\"y\":1},\"length\":4,\"direction\":\""
        + "VERTICAL\"},{\"coord\":{\"x\":1,\"y\":3},\"length\":3,\"direction\":\"VERTICAL\"}]}}"
        + System.lineSeparator() + "{\"method-name\":\"end-game\",\"arguments\":{}}"
        + System.lineSeparator();

    assertEquals(expected2, logToString());

  }

  /**
   * Check that the server returns the valid json when setup is sent
   */
  @Test
  public void testSetup() {
    // Prepare sample message
    Map<ShipType, Integer> shipCounts = Map.of(
        ShipType.CARRIER, 1,
        ShipType.BATTLESHIP, 1,
        ShipType.DESTROYER, 1,
        ShipType.SUBMARINE, 1
    );

    SetUpJson setUpJson = new SetUpJson(6, 6, shipCounts);
    JsonNode setup = this.mapper.convertValue(setUpJson, JsonNode.class);

    MessageJson setupServerRequest = new MessageJson("setup", setup);

    JsonNode sampleMessage = this.mapper.convertValue(setupServerRequest, JsonNode.class);

    EndGameJson endGameArgs =
        new EndGameJson(GameResult.WIN, "Player 1 sank all of Player 2's ships");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("end-game", endGameArgsNode);

    JsonNode endGameMsg = this.mapper.convertValue(endGameServerRequest, JsonNode.class);

    // Create the client with all necessary messages
    Mocket socket =
        new Mocket(this.testLog, List.of(sampleMessage.toString(), endGameMsg.toString()));

    // Create a Dealer
    try {
      Player player = new AiForServerPlayer(new Random(3));
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();


    String expected =
        "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\"x\":4,\"y\""
            +
            ":0},\"length\":6,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":2,\"y\":1},"
            + "\"length\":5,"
            + "\"direction"
            +
            "\":\"VERTICAL\"},{\"coord\":{\"x\":5,\"y\":1},\"length\":4,\"direction\":\"VERTICAL\"}"
            + ",{\"coord\""
            + ":{\"x\":1,\"y\":3},\"length\":3,\"direction\":\"VERTICAL\"}]}}"
            + System.lineSeparator()
            + "{\"method-name\":\"end-game\",\"arguments\":{}}" + System.lineSeparator();

    assertEquals(expected, logToString());
  }

  @Test
  public void testEndGame() {
    EndGameJson endGameArgs =
        new EndGameJson(GameResult.WIN, "Player 1 sank all of Player 2's ships");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("end-game", endGameArgsNode);

    JsonNode sampleMessage = this.mapper.convertValue(endGameServerRequest, JsonNode.class);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    // Create a Dealer
    try {
      Player player = new AiForServerPlayer(new Random());
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();

    try {
      socket.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

    String expected = "{\"method-name\":\"end-game\",\"arguments\":{}}" + System.lineSeparator();
    assertEquals(expected, logToString());
  }

  @Test
  public void testSuccesfulHits() {
    CoordinatesJson succesfulHits = new CoordinatesJson(
        new ArrayList<>(List.of(new Coord(1, 1),
            new Coord(2, 1))));
    JsonNode succHitsArgs = this.mapper.convertValue(succesfulHits, JsonNode.class);

    MessageJson successfulServerRequest = new MessageJson("successful-hits", succHitsArgs);

    JsonNode sampleMessage = this.mapper.convertValue(successfulServerRequest, JsonNode.class);

    EndGameJson endGameArgs =
        new EndGameJson(GameResult.WIN, "Player 1 sank all of Player 2's ships");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("end-game", endGameArgsNode);

    JsonNode endGameMsg = this.mapper.convertValue(endGameServerRequest, JsonNode.class);


    // Create the client with all necessary messages
    Mocket socket =
        new Mocket(this.testLog, List.of(sampleMessage.toString(), endGameMsg.toString()));

    // Create a Dealer
    try {
      Player player = new AiForServerPlayer(new Random());
      this.controller = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();

    try {
      socket.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

    String expected =
        "{\"method-name\":\"successful-hits\",\"arguments\":{}}" + System.lineSeparator()
            + "{\"method-name\":\"end-game\",\"arguments\":{}}" + System.lineSeparator();

    assertEquals(expected, logToString());
  }
}