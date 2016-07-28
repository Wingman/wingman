package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Player extends Character {
    Model getModel();

    void decodeAppearance(ByteBuffer arg0);

    int getSkillLevel();

    PlayerDefinition getDefinition();

    int getSkull();

    int getPrayerIcon();

    int getCombatLevel();

    String getName();

    @SuppressWarnings("all")
    interface Unsafe extends Character {
        void setSkillLevel(int value);

        void setDefinition(PlayerDefinition value);

        void setSkull(int value);

        void setPrayerIcon(int value);

        void setCombatLevel(int value);

        void setName(String value);
    }
}
