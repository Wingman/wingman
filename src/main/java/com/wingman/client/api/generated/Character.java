package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Character extends Entity {
    int[] getHitsplatDamages();

    int[] getHitsplatCycles();

    int getMaxHealth();

    int getX();

    int[] getQueueX();

    int getY();

    String getOverheadText();

    int[] getQueueY();

    int getAnimation();

    int getInteractingIndex();

    byte[] getQueueTransversed();

    @SuppressWarnings("all")
    interface Unsafe extends Entity {
        void setHitsplatDamages(int[] value);

        void setHitsplatCycles(int[] value);

        void setMaxHealth(int value);

        void setX(int value);

        void setQueueX(int[] value);

        void setY(int value);

        void setOverheadText(String value);

        void setQueueY(int[] value);

        void setAnimation(int value);

        void setInteractingIndex(int value);

        void setQueueTransversed(byte[] value);
    }
}
