package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface HitUpdate extends Node {
    int getHealthRatio();

    int getSpawnedLoopCycle();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setHealthRatio(int value);

        void setSpawnedLoopCycle(int value);
    }
}
