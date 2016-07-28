package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Player extends Character {
    Model getModel();

    void decodeAppearance(Buffer arg0);

    PlayerDefinition getDefinition();

    int getSkull();

    int getSkillLevel();

    int getCombatLevel();

    String getName();

    int getPrayerIcon();

    @SuppressWarnings("all")
    interface Unsafe extends Character {
        void setDefinition(PlayerDefinition value);

        void setSkull(int value);

        void setSkillLevel(int value);

        void setCombatLevel(int value);

        void setName(String value);

        void setPrayerIcon(int value);
    }
}
