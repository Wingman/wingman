package com.wingman.defaultplugins.devutils.game.world;

import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.generated.LandscapeTile;

import java.awt.*;

public class Scene {

    /**
     * Calculates the above ground height of a tile point.
     *
     * @param x the ground coordinate on the x axis
     * @param y the ground coordinate on the y axis
     * @param plane the client plane/ground level
     * @return the offset from the ground of the tile
     */
    public static int getTileHeight(int x, int y, int plane) {
        int x2 = x >> 7;
        int y2 = y >> 7;
        if (x2 > 0 && y2 > 0 && x2 < 103 && y2 < 103) {
            if (plane < 3 && (GameAPI.getTileSettings()[1][x2][y2] & 0x2) == 2) {
                plane++;
            }

            int[][][] tileHeights = GameAPI.getTileHeights();

            int aa = tileHeights[plane][x2][y2] * (128 - (x & 0x7F)) + tileHeights[plane][x2 + 1][y2] * (x & 0x7F) >> 7;
            int ab = tileHeights[plane][x2][y2 + 1] * (128 - (x & 0x7F)) + tileHeights[plane][x2 + 1][y2 + 1] * (x & 0x7F) >> 7;
            return aa * (128 - (y & 0x7F)) + ab * (y & 0x7F) >> 7;
        }
        return 0;
    }

    /**
     * Gets the coordinates of a tile, relative to the local player.
     *
     * @param tile the subject {@link LandscapeTile}
     * @return the point in the 3D world the tile is at, relative to the local player
     */
    public static Point getTilePosition(LandscapeTile tile) {
        return new Point(tile.getX() * 128, tile.getY() * 128);
    }

    /**
     * Calculates the distance in coordinates between two two-dimensional points.
     *
     * @param x the first x coordinate
     * @param y the first y coordinate
     * @param x2 the second x coordinate
     * @param y2 the second y coordinate
     * @return the distance between the two points
     */
    public static int getDistanceBetween(int x, int y, int x2, int y2) {
        return (int) Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2));
    }
}

