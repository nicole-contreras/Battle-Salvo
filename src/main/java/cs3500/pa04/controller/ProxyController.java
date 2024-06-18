package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetUpJson;
import cs3500.pa04.json.ShipAdapterJson;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameType;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The controller used to communicate to the server
 */
public class ProxyController implements Controller {

  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final ObjectMapper mapper = new ObjectMapper();
  private final Player player;

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if unable to get or output the stream
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }

  @Override
  public void run() {
    try {
      // creating a scanner but for Json - this.in represents incoming wire connection
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        // reads message
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message); // basically a switch
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
      e.printStackTrace();
    }
  }

  private void delegateMessage(MessageJson msg) {
    String name = msg.messageName();
    JsonNode arguments = msg.arguments();

    switch (name) {
      case "join" -> handleJoining();
      case "setup" -> handleSetUp(arguments);
      case "take-shots" -> handleTakeShots();
      case "report-damage" -> handleReportingDamage(arguments);
      case "successful-hits" -> handleSuccessfulHits(arguments);
      case "end-game" -> handleEndGame(arguments);
      default -> throw new IllegalStateException("Invalid message name");
    }
  }

  private void handleEndGame(JsonNode arguments) {
    // deserialize arguments
    EndGameJson endGameJson = this.mapper.convertValue(arguments, EndGameJson.class);

    // calling method
    player.endGame(endGameJson.result(), endGameJson.reason());

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode emptyJsonNode = objectMapper.createObjectNode();

    MessageJson msgRecord = new MessageJson("end-game", emptyJsonNode);
    JsonNode msgNode = this.mapper.convertValue(msgRecord, JsonNode.class);

    // sends it over the wire
    this.out.println(msgNode);
    try {
      server.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void handleSuccessfulHits(JsonNode arguments) {
    // deserialize arguments
    CoordinatesJson successfulHitsArgs = this.mapper.convertValue(arguments, CoordinatesJson.class);

    // calling method
    player.successfulHits(successfulHitsArgs.coords());


    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode emptyJsonNode = objectMapper.createObjectNode();

    MessageJson msgRecord = new MessageJson("successful-hits", emptyJsonNode);

    JsonNode msgNode = this.mapper.convertValue(msgRecord, JsonNode.class);


    // sends it over the wire
    this.out.println(msgNode);
  }

  private void handleReportingDamage(JsonNode arguments) {
    // deserialize arguments
    CoordinatesJson reportDamageArgs = this.mapper.convertValue(arguments, CoordinatesJson.class);

    // calling method
    List<Coord> coordList = player.reportDamage(reportDamageArgs.coords());

    CoordinatesJson coordinatesJson = new CoordinatesJson(coordList);

    // converts record into Json
    JsonNode jsonCoords = this.mapper.convertValue(coordinatesJson, JsonNode.class);

    MessageJson returnMessage = new MessageJson("report-damage", jsonCoords);
    JsonNode serializedVal = this.mapper.convertValue(returnMessage, JsonNode.class);


    // sends it over the wire
    this.out.println(serializedVal);
  }

  private void handleTakeShots() {
    try {
      //takes in values and makes it into a record
      CoordinatesJson coords = new CoordinatesJson(player.takeShots());

      // converts record into Json
      JsonNode jsonCoords = this.mapper.convertValue(coords, JsonNode.class);
      MessageJson returnMessage = new MessageJson("take-shots", jsonCoords);
      JsonNode serializedVal = this.mapper.convertValue(returnMessage, JsonNode.class);

      // sends it over the wire
      this.out.println(serializedVal);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void handleSetUp(JsonNode arguments) {
    // deserialize arguments
    SetUpJson setupArgs = this.mapper.convertValue(arguments, SetUpJson.class);

    // calling method
    List<Ship> shipList =
        player.setup(setupArgs.height(), setupArgs.width(), setupArgs.fleetSpec());

    List<ShipAdapterJson> shipAdapters = new ArrayList<>();

    //change design
    // convert to shipAdapter
    for (Ship ship : shipList) {
      shipAdapters.add(
          new ShipAdapterJson(new ShipAdapter(ship).getStart(), new ShipAdapter(ship).getLength(),
              new ShipAdapter(ship).getDire()));
    }

    //converting to record
    FleetJson fleet = new FleetJson(shipAdapters);

    // converts record into Json
    JsonNode setUpJson = this.mapper.convertValue(fleet, JsonNode.class);
    MessageJson returnMessage = new MessageJson("setup", setUpJson);
    JsonNode serializedVal = this.mapper.convertValue(returnMessage, JsonNode.class);

    // sends it over the wire
    this.out.println(serializedVal);

  }

  private void handleJoining() {
    //takes in values and makes it into a record
    JoinJson join = new JoinJson(player.name(), GameType.SINGLE);

    // converts record into Json
    JsonNode jsonResponse = this.mapper.convertValue(join, JsonNode.class);

    MessageJson returnValue = new MessageJson("join", jsonResponse);

    JsonNode serializedVal = this.mapper.convertValue(returnValue, JsonNode.class);

    // sends it over the wire
    this.out.println(serializedVal);
  }
}
