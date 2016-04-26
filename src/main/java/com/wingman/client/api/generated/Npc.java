package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Npc extends Character {
    NpcDefinition getDefinition();

    @SuppressWarnings("all")
    interface Unsafe extends Character {
        void setDefinition(NpcDefinition value);
    }
}
