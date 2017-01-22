package com.wingman.client.api.generated;

@SuppressWarnings("all")
public interface Landscape {
    void removeItemLayer(int plane, int x, int y);

    LandscapeTile[][][] getLandscapeTiles();

    @SuppressWarnings("all")
    interface Unsafe {
        void setLandscapeTiles(LandscapeTile[][][] value);
    }
}
