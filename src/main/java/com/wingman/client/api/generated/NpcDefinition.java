package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NpcDefinition extends DualNode {
    String[] getActions();

    int getCombatLevel();

    int getId();

    String getName();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setActions(String[] value);

        void setCombatLevel(int value);

        void setId(int value);

        void setName(String value);
    }
}
