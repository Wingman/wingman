package com.wingman.client.api.generated;

@SuppressWarnings("all")
public interface NPC extends Character {
    Model getModel();

    NPCDefinition getDefinition();

    @SuppressWarnings("all")
    interface Unsafe extends Character {
        void setDefinition(NPCDefinition value);
    }
}
