package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Character extends Entity {
    int[] getQueueY();

    int getAnimation();

    byte[] getQueueTransversed();

    int getMaxHealth();

    int getX();

    String getOverheadText();

    int getInteractingIndex();

    int[] getHitsplatDamages();

    int getY();

    int[] getQueueX();

    int[] getHitsplatCycles();

    int[] getHitsplatTypes();

    @SuppressWarnings("all")
    interface Unsafe extends Entity {
        void setQueueY(int[] value);

        void setAnimation(int value);

        void setQueueTransversed(byte[] value);

        void setMaxHealth(int value);

        void setX(int value);

        void setOverheadText(String value);

        void setInteractingIndex(int value);

        void setHitsplatDamages(int[] value);

        void setY(int value);

        void setQueueX(int[] value);

        void setHitsplatCycles(int[] value);

        void setHitsplatTypes(int[] value);
    }
}
