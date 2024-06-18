package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameType;

/**
 * A serializable join output.
 *
 * @param name the name of user
 * @param gameType the reason for the game ending
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType) {
}
