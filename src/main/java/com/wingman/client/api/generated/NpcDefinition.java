package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NpcDefinition extends DualNode {
    int getCombatLevel();

    String getName();

    int getId();

    String[] getActions();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setCombatLevel(int value);

        void setName(String value);

        void setId(int value);

        void setActions(String[] value);
    }
}
