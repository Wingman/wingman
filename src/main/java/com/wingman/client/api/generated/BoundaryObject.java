package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface BoundaryObject {
    int getHash();

    Entity getEntity();

    int getX();

    int getConfig();

    int getPlane();

    Entity getEntity2();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe {
        void setHash(int value);

        void setEntity(Entity value);

        void setX(int value);

        void setConfig(int value);

        void setPlane(int value);

        void setEntity2(Entity value);

        void setY(int value);
    }
}
