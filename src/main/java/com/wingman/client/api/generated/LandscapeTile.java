package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LandscapeTile extends Node {
    ShapedTile getShapedTile();

    GroundObject getGroundObject();

    WallObject getWallObject();

    int getX();

    int getPlane();

    PlainTile getPlainTile();

    InteractableObject[] getInteractableObjects();

    BoundaryObject getBoundaryObject();

    int[] getObjectFlags();

    ItemLayer getItemLayer();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setShapedTile(ShapedTile value);

        void setGroundObject(GroundObject value);

        void setWallObject(WallObject value);

        void setX(int value);

        void setPlane(int value);

        void setPlainTile(PlainTile value);

        void setInteractableObjects(InteractableObject[] value);

        void setBoundaryObject(BoundaryObject value);

        void setObjectFlags(int[] value);

        void setItemLayer(ItemLayer value);

        void setY(int value);
    }
}
