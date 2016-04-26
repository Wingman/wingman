package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Player extends Character {
    int getCombatLevel();

    PlayerDefinition getDefinition();

    String getName();

    int getPrayerIcon();

    int getSkillLevel();

    int getSkull();

    void decodeAppearance(Buffer arg0);

    Model getModel();

    @SuppressWarnings("all")
    interface Unsafe extends Character {
        void setCombatLevel(int value);

        void setDefinition(PlayerDefinition value);

        void setName(String value);

        void setPrayerIcon(int value);

        void setSkillLevel(int value);

        void setSkull(int value);
    }
}
