package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface BoundaryObject {
    int getX();

    Entity getEntity2();

    int getHash();

    int getY();

    Entity getEntity();

    int getPlane();

    int getConfig();

    @SuppressWarnings("all")
    interface Unsafe {
        void setX(int value);

        void setEntity2(Entity value);

        void setHash(int value);

        void setY(int value);

        void setEntity(Entity value);

        void setPlane(int value);

        void setConfig(int value);
    }
}
