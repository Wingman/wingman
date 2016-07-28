package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemLayer {
    Entity getMiddle();

    int getY();

    int getX();

    Entity getTop();

    Entity getBottom();

    int getHash();

    int getPlane();

    int getHeight();

    @SuppressWarnings("all")
    interface Unsafe {
        void setMiddle(Entity value);

        void setY(int value);

        void setX(int value);

        void setTop(Entity value);

        void setBottom(Entity value);

        void setHash(int value);

        void setPlane(int value);

        void setHeight(int value);
    }
}
