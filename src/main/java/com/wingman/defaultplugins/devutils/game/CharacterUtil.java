package com.wingman.defaultplugins.devutils.game;

import com.wingman.client.api.generated.Character;
import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.generated.HealthBar;
import com.wingman.client.api.generated.HitUpdate;

import java.util.Optional;

/**
 * Provides API related to {@link Character}.
 */
public class CharacterUtil {

    /**
     * Gets the health of a {@link Character} represented as a floating point number between 0 and 1,
     * or -1 if the subject's health could not be retrieved: <br>
     *
     * The value returned is only guaranteed to be up to date if the subject's health bar is visible.
     *
     * @param ofCharacter the character to get health percentage of
     * @return a value from 0 to 1;
     *         or nothing if the health could not be retrieved
     */
    public static Optional<Float> getHealthDecimal(Character ofCharacter) {
        for (Object healthBarObject : ofCharacter.getHealthBars()) {
            HealthBar healthBar = (HealthBar) healthBarObject;

            for (Object hitUpdateObject : healthBar.getHitUpdates()) {
                HitUpdate hitUpdate = (HitUpdate) hitUpdateObject;

                int healthRatio = hitUpdate.getHealthRatio();

                if (healthRatio == 0) {
                    return Optional.of(0F);
                }

                return Optional.of(healthRatio / (float) healthBar.getDefinition().getWidth());
            }
        }

        return Optional.empty();
    }

    /**
     * Gets the health percentage of a {@link Character}.
     * The value returned is only guaranteed to be up to date if the subject's health bar is visible.
     *
     * @param ofCharacter the character to get health percentage of
     * @return a value from 0 to 100 to signify health percentage;
     *         or nothing if the health percentage could not be retrieved
     */
    public static Optional<Integer> getHealthPercentage(Character ofCharacter) {
        return getHealthDecimal(ofCharacter)
                .map(decimal -> (int) (decimal * 100));
    }

    /**
     * @param ofCharacter the character to get target of
     * @return the target of the subject;
     *         or nothing if they have no target
     */
    public static Optional<Character> getTargetCharacter(Character ofCharacter) {
        int interactingIndex = ofCharacter.getInteractingIndex();

        if (interactingIndex == -1) {
            return Optional.empty();
        }

        if (interactingIndex < 32768) {
            return Optional.of(GameAPI.getNpcs()[interactingIndex]);
        } else {
            return Optional.of(GameAPI.getPlayers()[interactingIndex - 32768]);
        }
    }
}
