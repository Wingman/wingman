package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NPCDefinition extends DualNode {
    String[] getActions();

    int getCombatLevel();

    int getId();

    String getName();

    int getOverHeadIcon();

    boolean getVisible();

    boolean getVisibleOnMiniMap();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setActions(String[] value);

        void setCombatLevel(int value);

        void setId(int value);

        void setName(String value);

        void setOverHeadIcon(int value);

        void setVisible(boolean value);

        void setVisibleOnMiniMap(boolean value);
    }
}
