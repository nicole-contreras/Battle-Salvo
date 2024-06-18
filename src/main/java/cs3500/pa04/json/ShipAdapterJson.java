package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Directions;

/**
 * A serializable SetUp arguments
 *
 * @param start the height of the board
 * @param length the length of the ship
 * @param dire the direction of the ship
 */
public record ShipAdapterJson(
    @JsonProperty("coord") Coord start,
    @JsonProperty("length") int length,
    @JsonProperty("direction") Directions dire
) {
}
