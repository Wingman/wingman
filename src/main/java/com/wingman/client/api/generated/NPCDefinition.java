package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NPCDefinition extends DoublyNode {
    boolean getVisible();

    int getCombatLevel();

    String[] getActions();

    boolean getVisibleOnMiniMap();

    int getOverHeadIcon();

    String getName();

    @SuppressWarnings("all")
    interface Unsafe extends DoublyNode {
        void setVisible(boolean value);

        void setCombatLevel(int value);

        void setActions(String[] value);

        void setVisibleOnMiniMap(boolean value);

        void setOverHeadIcon(int value);

        void setName(String value);
    }
}
