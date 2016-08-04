package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Character extends Entity {
    int getAnimation();

    NodeIterable getHealthBars();

    int getInteractingIndex();

    int getX();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe extends Entity {
        void setAnimation(int value);

        void setHealthBars(NodeIterable value);

        void setInteractingIndex(int value);

        void setX(int value);

        void setY(int value);
    }
}
