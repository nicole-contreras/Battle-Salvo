package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * A serializable fleet.
 *
 * @param ships the list of shipAdapterJSON
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipAdapterJson> ships) {
}
