package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NpcDefinition extends DualNode {
    String[] getActions();

    int getId();

    int getCombatLevel();

    String getName();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setActions(String[] value);

        void setId(int value);

        void setCombatLevel(int value);

        void setName(String value);
    }
}
