package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import java.util.List;

/**
 * A serializable list of coordinates.
 *
 * @param coords the coordinates
 */
public record CoordinatesJson(
    @JsonProperty("coordinates") List<Coord> coords) {
}
