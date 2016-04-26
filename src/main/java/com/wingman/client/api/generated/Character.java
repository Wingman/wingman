package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Character extends Entity {
    int getAnimation();

    int getCurrentHealth();

    int[] getHitsplatCycles();

    int[] getHitsplatDamages();

    int[] getHitsplatTypes();

    int getInteractingIndex();

    int getMaxHealth();

    String getOverheadText();

    byte[] getQueueTransversed();

    int[] getQueueX();

    int[] getQueueY();

    int getX();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe extends Entity {
        void setAnimation(int value);

        void setCurrentHealth(int value);

        void setHitsplatCycles(int[] value);

        void setHitsplatDamages(int[] value);

        void setHitsplatTypes(int[] value);

        void setInteractingIndex(int value);

        void setMaxHealth(int value);

        void setOverheadText(String value);

        void setQueueTransversed(byte[] value);

        void setQueueX(int[] value);

        void setQueueY(int[] value);

        void setX(int value);

        void setY(int value);
    }
}
