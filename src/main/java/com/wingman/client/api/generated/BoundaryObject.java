package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface BoundaryObject {
    int getHash();

    int getX();

    Entity getEntity2();

    int getY();

    int getPlane();

    int getConfig();

    Entity getEntity();

    @SuppressWarnings("all")
    interface Unsafe {
        void setHash(int value);

        void setX(int value);

        void setEntity2(Entity value);

        void setY(int value);

        void setPlane(int value);

        void setConfig(int value);

        void setEntity(Entity value);
    }
}
