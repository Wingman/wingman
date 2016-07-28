package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemLayer {
    Entity getTop();

    int getX();

    int getHash();

    int getHeight();

    Entity getMiddle();

    Entity getBottom();

    int getY();

    int getPlane();

    @SuppressWarnings("all")
    interface Unsafe {
        void setTop(Entity value);

        void setX(int value);

        void setHash(int value);

        void setHeight(int value);

        void setMiddle(Entity value);

        void setBottom(Entity value);

        void setY(int value);

        void setPlane(int value);
    }
}
