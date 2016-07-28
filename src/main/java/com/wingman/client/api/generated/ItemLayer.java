package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemLayer {
    Entity getBottom();

    int getPlane();

    int getHeight();

    int getHash();

    Entity getTop();

    int getY();

    int getX();

    Entity getMiddle();

    @SuppressWarnings("all")
    interface Unsafe {
        void setBottom(Entity value);

        void setPlane(int value);

        void setHeight(int value);

        void setHash(int value);

        void setTop(Entity value);

        void setY(int value);

        void setX(int value);

        void setMiddle(Entity value);
    }
}
