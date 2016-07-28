package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LandscapeTile extends Node {
    PlainTile getPlainTile();

    ItemLayer getItemLayer();

    BoundaryObject getBoundaryObject();

    GroundObject getGroundObject();

    int getY();

    int getPlane();

    int getX();

    WallObject getWallObject();

    int[] getObjectFlags();

    InteractableObject[] getInteractableObjects();

    ShapedTile getShapedTile();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setPlainTile(PlainTile value);

        void setItemLayer(ItemLayer value);

        void setBoundaryObject(BoundaryObject value);

        void setGroundObject(GroundObject value);

        void setY(int value);

        void setPlane(int value);

        void setX(int value);

        void setWallObject(WallObject value);

        void setObjectFlags(int[] value);

        void setInteractableObjects(InteractableObject[] value);

        void setShapedTile(ShapedTile value);
    }
}
