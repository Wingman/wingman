package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Character extends Entity {
    byte[] getQueueTransversed();

    int[] getHitsplatCycles();

    int getInteractingIndex();

    String getOverheadText();

    int getY();

    int getAnimation();

    int[] getQueueY();

    int[] getHitsplatTypes();

    int[] getHitsplatDamages();

    int getMaxHealth();

    int getX();

    int[] getQueueX();

    @SuppressWarnings("all")
    interface Unsafe extends Entity {
        void setQueueTransversed(byte[] value);

        void setHitsplatCycles(int[] value);

        void setInteractingIndex(int value);

        void setOverheadText(String value);

        void setY(int value);

        void setAnimation(int value);

        void setQueueY(int[] value);

        void setHitsplatTypes(int[] value);

        void setHitsplatDamages(int[] value);

        void setMaxHealth(int value);

        void setX(int value);

        void setQueueX(int[] value);
    }
}
