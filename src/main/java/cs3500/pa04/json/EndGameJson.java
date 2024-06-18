package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.GameResult;

/**
 * A serializable end game input.
 *
 * @param result the result
 * @param reason the reason for the game ending
 */
public record EndGameJson(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason
) {
}
