package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.ShipType;
import java.util.Map;

/**
 * A serializable SetUp arguments
 *
 * @param height the height of the board
 * @param width the width of the board
 * @param fleetSpec the specifications of the ship number
 */
public record SetUpJson(
    @JsonProperty("height") int height,
    @JsonProperty("width") int width,
    @JsonProperty("fleet-spec")  Map<ShipType, Integer> fleetSpec) {
}
