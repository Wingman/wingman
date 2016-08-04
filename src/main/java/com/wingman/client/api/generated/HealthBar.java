package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface HealthBar extends Node {
    NodeIterable getHitUpdates();

    HealthBarDefinition getDefinition();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setHitUpdates(NodeIterable value);

        void setDefinition(HealthBarDefinition value);
    }
}
