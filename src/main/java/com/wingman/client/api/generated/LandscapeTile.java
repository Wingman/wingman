package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LandscapeTile extends Node {
    InteractableObject[] getInteractableObjects();

    int[] getObjectFlags();

    int getPlane();

    int getX();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setInteractableObjects(InteractableObject[] value);

        void setObjectFlags(int[] value);

        void setPlane(int value);

        void setX(int value);

        void setY(int value);
    }
}
