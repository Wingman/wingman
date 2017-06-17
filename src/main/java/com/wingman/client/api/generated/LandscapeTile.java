package com.wingman.client.api.generated;

@SuppressWarnings("all")
public interface LandscapeTile extends Node {
    InteractableObject[] getInteractableObjects();

    ItemLayer getItemLayer();

    int[] getObjectFlags();

    int getPlane();

    int getX();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setInteractableObjects(InteractableObject[] value);

        void setItemLayer(ItemLayer value);

        void setObjectFlags(int[] value);

        void setPlane(int value);

        void setX(int value);

        void setY(int value);
    }
}
