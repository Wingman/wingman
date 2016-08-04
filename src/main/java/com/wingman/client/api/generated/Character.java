package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Character extends Entity {
    int getX();

    int getInteractingIndex();

    int getY();

    NodeIterable getHealthBars();

    int getAnimation();

    @SuppressWarnings("all")
    interface Unsafe extends Entity {
        void setX(int value);

        void setInteractingIndex(int value);

        void setY(int value);

        void setHealthBars(NodeIterable value);

        void setAnimation(int value);
    }
}
