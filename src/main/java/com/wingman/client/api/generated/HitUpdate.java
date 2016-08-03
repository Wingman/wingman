package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface HitUpdate extends Node {
    int getSpawnedLoopCycle();

    int getHealthRatio();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setSpawnedLoopCycle(int value);

        void setHealthRatio(int value);
    }
}
