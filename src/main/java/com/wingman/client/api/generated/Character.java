package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Character extends Entity {
    int getAnimation();

    int[] getInteractingIndex();

    String getOverheadText();

    int[] getQueueTransversed();

    int getX();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe extends Entity {
        void setAnimation(int value);

        void setInteractingIndex(int[] value);

        void setOverheadText(String value);

        void setQueueTransversed(int[] value);

        void setX(int value);

        void setY(int value);
    }
}
