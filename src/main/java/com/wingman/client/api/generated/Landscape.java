package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Landscape {
    void addItemLayer(int arg0, int arg1, int arg2, int arg3, Entity arg4, int arg5, Entity arg6, Entity arg7);

    void removeItemLayer(int arg0, int arg1, int arg2);

    LandscapeTile[][][] getLandscapeTiles();

    int[][][] getHeightMap();

    int getPlaneAmount();

    int getMapSizeX();

    int getMapSizeY();

    @SuppressWarnings("all")
    interface Unsafe {
        void setLandscapeTiles(LandscapeTile[][][] value);

        void setHeightMap(int[][][] value);

        void setPlaneAmount(int value);

        void setMapSizeX(int value);

        void setMapSizeY(int value);
    }
}
