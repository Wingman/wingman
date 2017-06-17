package com.wingman.defaultplugins.devutils.game;

import com.wingman.client.api.generated.Character;
import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.generated.HealthBar;
import com.wingman.client.api.generated.HitUpdate;

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
     * @return a value from 0 to 1,
     *         or -1 if the health could not be retrieved
     */
    public static float getHealthDecimal(Character ofCharacter) {
        for (Object healthBarObject : ofCharacter.getHealthBars()) {
            HealthBar healthBar = (HealthBar) healthBarObject;

            for (Object hitUpdateObject : healthBar.getHitUpdates()) {
                HitUpdate hitUpdate = (HitUpdate) hitUpdateObject;

                int healthRatio = hitUpdate.getHealthRatio();
                if (healthRatio == 0) {
                    return 0;
                }

                return healthRatio / (float) healthBar.getDefinition().getWidth();
            }
        }

        return -1;
    }

    /**
     * Gets the health percentage of a {@link Character}.
     * The value returned is only guaranteed to be up to date if the subject's health bar is visible.
     *
     * @param ofCharacter the character to get health percentage of
     * @return a value from 0 to 100 to signify health percentage,
     *         or -1 if the health percentage could not be retrieved
     */
    public static int getHealthPercentage(Character ofCharacter) {
        float decimal = getHealthDecimal(ofCharacter);

        if (decimal != -1) {
            return (int) (decimal * 100);
        }

        return -1;
    }

    /**
     * @param ofCharacter the character to get target of
     * @return the target of the subject,
     *         or {@code null} if they have no target
     */
    public static Character getTargetCharacter(Character ofCharacter) {
        int interactingIndex = ofCharacter.getInteractingIndex();

        if (interactingIndex == -1) {
            return null;
        }

        if (interactingIndex < 32768) {
            return GameAPI.getNpcs()[interactingIndex];
        } else {
            return GameAPI.getPlayers()[interactingIndex - 32768];
        }
    }
}
