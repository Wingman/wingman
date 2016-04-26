package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface BoundaryObject {
    int getConfig();

    Entity getEntity();

    Entity getEntity2();

    int getHash();

    int getPlane();

    int getX();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe {
        void setConfig(int value);

        void setEntity(Entity value);

        void setEntity2(Entity value);

        void setHash(int value);

        void setPlane(int value);

        void setX(int value);

        void setY(int value);
    }
}
