package com.wingman.client.api.overlay;

import java.awt.*;

public class Overlay {

    private static final Point DEFAULT_POSITION = new Point(0, 0);
    private static final Dimension DEFAULT_DIMENSION = new Dimension(200, 200);

    public final boolean isMovable;
    public final DrawOrder drawOrder;

    public int userOffsetX;
    public int userOffsetY;

    public int[] cachedPixels;

    public Overlay() {
        this(true);
    }

    public Overlay(boolean isMovable) {
        this(isMovable, DrawOrder.ALWAYS_ON_TOP);
    }

    public Overlay(boolean isMovable, DrawOrder drawOrder) {
        this.isMovable = isMovable;
        this.drawOrder = drawOrder;
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

    public enum DrawOrder {
        ALWAYS_ON_TOP,
        UNDER_2D_UI
    }
}
