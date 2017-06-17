package com.wingman.client.api.generated;

@SuppressWarnings("all")
public interface ItemLayer {
    Entity getBottom();

    int getHeight();

    int getX();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe {
        void setBottom(Entity value);

        void setHeight(int value);

        void setX(int value);

        void setY(int value);
    }
}
