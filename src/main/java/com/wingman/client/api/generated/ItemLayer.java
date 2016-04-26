package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemLayer {
    Entity getBottom();

    int getHash();

    int getHeight();

    Entity getMiddle();

    int getPlane();

    Entity getTop();

    int getX();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe {
        void setBottom(Entity value);

        void setHash(int value);

        void setHeight(int value);

        void setMiddle(Entity value);

        void setPlane(int value);

        void setTop(Entity value);

        void setX(int value);

        void setY(int value);
    }
}
