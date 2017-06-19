package com.wingman.defaultplugins.devutils.game.world;

import com.wingman.client.api.generated.GameAPI;

import java.awt.*;

public class Perspective {

    /*
        The engine divides the unit circle
        into a number of units equal in size

        This is presumably to gain extra precision when
        making use of the sine and cosine trigonometric
        functions while using fixed point arithmetic.

        If this isn't done, degrees can only be
        represented as integers, and it won't be
        able to make use of fractions of a degree.

        If there are 2048 units, one unit
        is equivalent to 0.17578125 degrees.
     */
    private static final int CIRCLE_UNITS = 2048;
    private static final double CIRCLE_UNIT = (2 * Math.PI) / CIRCLE_UNITS;

    /*
        We use a lookup table for the sine and
        cosine functions in order to improve execution
        speed of methods that use the functions.
     */
    private static int[] SIN_TABLE = new int[CIRCLE_UNITS];
    private static int[] COS_TABLE = new int[CIRCLE_UNITS];

    static {
        /*
            Use scaling factor of 1 / 2^16 to preserve some
            precision from the sine and cosine functions.
         */
        for (int i = 0; i < CIRCLE_UNITS; i++) {
            SIN_TABLE[i] = (int) (65536D * Math.sin((double) i * CIRCLE_UNIT));
            COS_TABLE[i] = (int) (65536D * Math.cos((double) i * CIRCLE_UNIT));
        }
    }

    /**
     * Translates two-dimensional ground coordinates within the 3D
     * world to their corresponding coordinates on the game screen.
     *
     * @param x           ground coordinate on the x axis
     * @param y           ground coordinate on the y axis
     * @param clientPlane the client plane, ground level
     * @param zOffset     the offset from the ground
     * @return a {@link Point} on screen corresponding to the position in 3D-space
     */
    public static Point worldToScreen(int x, int y, int clientPlane, int zOffset) {
        // Is the point inside the landscape?
        if (x >= 128 && y >= 128 && x <= 13056 && y <= 13056) {
            int z = Scene.getTileHeight(x, y, clientPlane);
            // Translate relative to the the camera (the origin)
            x -= GameAPI.getCameraX();
            y -= GameAPI.getCameraY();
            z -= GameAPI.getCameraZ();
            z -= zOffset;

            /*
                Units rotated around respective axis

                0 = 0 or 360 deg
                512 = 90 deg
                1024 = 180 deg
                2047 = 359.82421875 deg

                Pitch is locked to between
                128 and 383 units,
                (22.5 and 67.32421875 deg)
             */
            int cameraYaw = GameAPI.getCameraYaw();
            int cameraPitch = GameAPI.getCameraPitch();

            // Rotation around x axis
            int pitchSin = SIN_TABLE[cameraPitch];
            int pitchCos = COS_TABLE[cameraPitch];

            // Rotation around y axis
            int yawSin = SIN_TABLE[cameraYaw];
            int yawCos = COS_TABLE[cameraYaw];

            /*
                Read: https://en.wikipedia.org/wiki/3D_projection#Perspective_projection

                These must be substituted in the formulas from the Wikipedia article:
                (Left hand orientation to RS orientation)

                x = x
                y = z
                z = -y

                Rotation around z-axis (roll) is not used in RS (angle is always 0 deg),
                which means that Cz = COS_TABLE[0] = 65536 (that >> 16 is 1)
                and Sz = SIN_TABLE[0] = 0, so they can be cancelled out.

                transformedZ (the z distance - dz on wikipedia) is multiplied by -1 for whatever reason.
             */
            int intermediate = yawCos * y - yawSin * x >> 16;

            int transformedX = yawCos * x + yawSin * y >> 16;
            int transformedY = pitchCos * z - pitchSin * intermediate >> 16;
            int transformedZ = pitchSin * z + pitchCos * intermediate >> 16;

            if (transformedZ >= 50) {
                /*
                    Transform to screen according to
                    https://en.wikipedia.org/wiki/3D_projection#Diagram
                 */

                int focalLength = GameAPI.getViewPortScale();

                int screenX = GameAPI.getViewPortWidth() / 2
                        + transformedX * focalLength / transformedZ;

                int screenY = GameAPI.getViewPortHeight() / 2
                        + transformedY * focalLength / transformedZ;

                return new Point(screenX, screenY);
            }
        }

        return new Point(-1, -1);
    }

    /**
     * Translates two-dimensional ground coordinates within the 3D
     * world to their corresponding coordinates on the mini-map.
     *
     * @param x ground coordinate on the x axis
     * @param y ground coordinate on the y axis
     * @return a {@link Point} on the mini-map corresponding to the position in 3D-space
     */
    public static Point worldToMiniMap(int x, int y) {
        int angle = GameAPI.getMapScale() + GameAPI.getMapAngle() & 0x7FF;
        x = x / 32 - GameAPI.getLocalPlayer().getX() / 32;
        y = y / 32 - GameAPI.getLocalPlayer().getY() / 32;

        int dist = x * x + y * y;

        if (dist < 6400) {
            int sin = SIN_TABLE[angle];
            int cos = COS_TABLE[angle];

            sin = sin * 256 / (GameAPI.getMapOffset() + 256);
            cos = cos * 256 / (GameAPI.getMapOffset() + 256);

            int xx = y * sin + cos * x >> 16;
            int yy = sin * x - y * cos >> 16;

            int miniMapDiameter = !GameAPI.getResizableMode() ? 208 : 167;

            int miniMapX = GameAPI.getAppletWidth() - miniMapDiameter;

            // Get center-point of the mini-map
            x = (miniMapX + miniMapDiameter / 2) + xx;
            y = (miniMapDiameter / 2 - 1) + yy;

            return new Point(x, y);
        }

        return new Point(-1, -1);
    }
}
