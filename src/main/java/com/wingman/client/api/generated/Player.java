package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Player extends Character {
    Model getModel();

    void decodeAppearance(Buffer arg0);

    int getCombatLevel();

    int getSkull();

    String getName();

    int getPrayerIcon();

    int getSkillLevel();

    PlayerDefinition getDefinition();

    @SuppressWarnings("all")
    interface Unsafe extends Character {
        void setCombatLevel(int value);

        void setSkull(int value);

        void setName(String value);

        void setPrayerIcon(int value);

        void setSkillLevel(int value);

        void setDefinition(PlayerDefinition value);
    }
}
