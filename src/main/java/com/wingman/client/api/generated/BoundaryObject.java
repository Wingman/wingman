package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface BoundaryObject {
    int getPlane();

    Entity getEntity();

    Entity getEntity2();

    int getY();

    int getHash();

    int getConfig();

    int getX();

    @SuppressWarnings("all")
    interface Unsafe {
        void setPlane(int value);

        void setEntity(Entity value);

        void setEntity2(Entity value);

        void setY(int value);

        void setHash(int value);

        void setConfig(int value);

        void setX(int value);
    }
}
