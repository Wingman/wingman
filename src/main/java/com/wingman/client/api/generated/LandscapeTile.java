package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LandscapeTile extends Node {
    ItemLayer getItemLayer();

    int getPlane();

    int[] getObjectFlags();

    int getX();

    WallObject getWallObject();

    ShapedTile getShapedTile();

    GroundObject getGroundObject();

    BoundaryObject getBoundaryObject();

    int getY();

    InteractableObject[] getInteractableObjects();

    PlainTile getPlainTile();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setItemLayer(ItemLayer value);

        void setPlane(int value);

        void setObjectFlags(int[] value);

        void setX(int value);

        void setWallObject(WallObject value);

        void setShapedTile(ShapedTile value);

        void setGroundObject(GroundObject value);

        void setBoundaryObject(BoundaryObject value);

        void setY(int value);

        void setInteractableObjects(InteractableObject[] value);

        void setPlainTile(PlainTile value);
    }
}
