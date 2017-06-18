package com.wingman.client.api.overlay;

import java.awt.*;

public class Overlay {

    private static final Point DEFAULT_POSITION = new Point(0, 0);
    private static final Dimension DEFAULT_DIMENSION = new Dimension(200, 200);

    public final boolean isMovable;

    public int userOffsetX;
    public int userOffsetY;

    public int[] cachedPixels;

    public Overlay(boolean isMovable) {
        this.isMovable = isMovable;
    }

    public Point getPosition() {
        return DEFAULT_POSITION;
    }

    public Dimension getDimension() {
        return DEFAULT_DIMENSION;
    }

    public boolean shouldDraw() {
        return true;
    }

    public boolean shouldUpdate() {
        return false;
    }

    public void update(Graphics2D g) {
        // Override to implement
    }
}
