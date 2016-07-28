package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Player extends Character {
    void decodeAppearance(Buffer arg0);

    Model getModel();

    int getSkillLevel();

    String getName();

    int getPrayerIcon();

    PlayerDefinition getDefinition();

    int getSkull();

    int getCombatLevel();

    @SuppressWarnings("all")
    interface Unsafe extends Character {
        void setSkillLevel(int value);

        void setName(String value);

        void setPrayerIcon(int value);

        void setDefinition(PlayerDefinition value);

        void setSkull(int value);

        void setCombatLevel(int value);
    }
}
