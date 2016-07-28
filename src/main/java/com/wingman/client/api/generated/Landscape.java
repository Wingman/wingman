package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Landscape {
    void removeItemLayer(int arg0, int arg1, int arg2);

    void addItemLayer(int arg0, int arg1, int arg2, int arg3, Entity arg4, int arg5, Entity arg6, Entity arg7);

    int getPlaneAmount();

    int getMapSizeY();

    int getMapSizeX();

    int[][][] getHeightMap();

    LandscapeTile[][][] getLandscapeTiles();

    @SuppressWarnings("all")
    interface Unsafe {
        void setPlaneAmount(int value);

        void setMapSizeY(int value);

        void setMapSizeX(int value);

        void setHeightMap(int[][][] value);

        void setLandscapeTiles(LandscapeTile[][][] value);
    }
}
