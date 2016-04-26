package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Landscape {
    int[][][] getHeightMap();

    LandscapeTile[][][] getLandscapeTiles();

    int getMapSizeX();

    int getMapSizeY();

    int getPlaneAmount();

    void addItemLayer(int arg0, int arg1, int arg2, int arg3, Entity arg4, int arg5, Entity arg6, Entity arg7);

    void removeItemLayer(int arg0, int arg1, int arg2);

    @SuppressWarnings("all")
    interface Unsafe {
        void setHeightMap(int[][][] value);

        void setLandscapeTiles(LandscapeTile[][][] value);

        void setMapSizeX(int value);

        void setMapSizeY(int value);

        void setPlaneAmount(int value);
    }
}
