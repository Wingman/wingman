package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface WallObject {
    Entity getEntity();

    int getOrientation();

    @SuppressWarnings("all")
    interface Unsafe {
        void setEntity(Entity value);

        void setOrientation(int value);
    }
}
