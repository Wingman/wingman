package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NPCDefinition extends DoublyNode {
    boolean getVisibleOnMiniMap();

    int getCombatLevel();

    String getName();

    boolean getVisible();

    int getOverHeadIcon();

    String[] getActions();

    @SuppressWarnings("all")
    interface Unsafe extends DoublyNode {
        void setVisibleOnMiniMap(boolean value);

        void setCombatLevel(int value);

        void setName(String value);

        void setVisible(boolean value);

        void setOverHeadIcon(int value);

        void setActions(String[] value);
    }
}
