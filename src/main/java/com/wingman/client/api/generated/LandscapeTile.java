package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LandscapeTile extends Node {
    BoundaryObject getBoundaryObject();

    GroundObject getGroundObject();

    InteractableObject[] getInteractableObjects();

    ItemLayer getItemLayer();

    int[] getObjectFlags();

    PlainTile getPlainTile();

    int getPlane();

    ShapedTile getShapedTile();

    WallObject getWallObject();

    int getX();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setBoundaryObject(BoundaryObject value);

        void setGroundObject(GroundObject value);

        void setInteractableObjects(InteractableObject[] value);

        void setItemLayer(ItemLayer value);

        void setObjectFlags(int[] value);

        void setPlainTile(PlainTile value);

        void setPlane(int value);

        void setShapedTile(ShapedTile value);

        void setWallObject(WallObject value);

        void setX(int value);

        void setY(int value);
    }
}
