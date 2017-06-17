package com.wingman.defaultplugins.devutils.util;

import com.wingman.defaultplugins.devutils.game.world.Perspective;

import java.awt.*;

/**
 * Provides API for simplifying the process of drawing on the game's {@link Graphics} objects.
 */
public class Painting {

    /**
     * Draws a transparent polygon over a tile.
     *
     * @param graphics {@link Graphics} object on which the overlay should be drawn
     * @param x center x coordinate of the tile
     * @param y center y coordinate of the tile
     * @param plane plane on which the tile exists
     * @param zOffset offset on the z axis of the drawn polygon
     * @param outlineColor color of the outline of the polygon
     * @param fillColor color of the inside of the polygon
     * @param lineThickness thickness of the line around the polygon
     */
    public static void drawTileOverlay(Graphics graphics, int x, int y, int zOffset, int plane, Color outlineColor, Color fillColor, float lineThickness) {
        Point p1 = Perspective.worldToScreen(x - 64, y - 64, plane, zOffset);
        Point p2 = Perspective.worldToScreen(x - 64, y + 64, plane, zOffset);
        Point p3 = Perspective.worldToScreen(x + 64, y + 64, plane, zOffset);
        Point p4 = Perspective.worldToScreen(x + 64, y - 64, plane, zOffset);

        Polygon p = new Polygon();
        p.addPoint(p1.x, p1.y);
        p.addPoint(p2.x, p2.y);
        p.addPoint(p3.x, p3.y);
        p.addPoint(p4.x, p4.y);

        Graphics2D g2 = (Graphics2D) graphics;
        Stroke oldStroke = g2.getStroke();

        g2.setStroke(new BasicStroke(lineThickness));
        g2.setColor(outlineColor);
        g2.drawPolygon(p);
        g2.setStroke(oldStroke);
        g2.setColor(fillColor);
        g2.fillPolygon(p);
    }

    /**
     * Draws a transparent polygon box over a tile.
     *
     * @param graphics graphics object on which the overlay should be drawn
     * @param x center x coordinate of the tile
     * @param y center y coordinate of the tile
     * @param height the height of the box
     * @param plane plane on which the tile exists
     * @param zOffset offset on the z axis of the drawn polygon
     * @param outlineColor color of the outline of the polygon
     * @param fillColor color of the inside of the polygon
     * @param lineThickness thickness of the line around the polygon
     */
    public static void drawTileOverlayBox(Graphics graphics, int x, int y, int zOffset, int plane, int height, Color outlineColor, Color fillColor, float lineThickness) {
        Point p1 = Perspective.worldToScreen(x - 64, y - 64, plane, zOffset);
        Point p2 = Perspective.worldToScreen(x - 64, y + 64, plane, zOffset);
        Point p3 = Perspective.worldToScreen(x + 64, y + 64, plane, zOffset);
        Point p4 = Perspective.worldToScreen(x + 64, y - 64, plane, zOffset);

        Point p5 = Perspective.worldToScreen(x - 64, y - 64, plane, zOffset + height);
        Point p6 = Perspective.worldToScreen(x - 64, y + 64, plane, zOffset + height);
        Point p7 = Perspective.worldToScreen(x + 64, y + 64, plane, zOffset + height);
        Point p8 = Perspective.worldToScreen(x + 64, y - 64, plane, zOffset + height);

        Polygon polygon = new Polygon(); // bottom
        polygon.addPoint(p1.x, p1.y);
        polygon.addPoint(p2.x, p2.y);
        polygon.addPoint(p3.x, p3.y);
        polygon.addPoint(p4.x, p4.y);

        Polygon polygon2 = new Polygon(); // top
        polygon2.addPoint(p5.x, p5.y);
        polygon2.addPoint(p6.x, p6.y);
        polygon2.addPoint(p7.x, p7.y);
        polygon2.addPoint(p8.x, p8.y);

        Polygon polygon3 = new Polygon(); // wall
        polygon3.addPoint(p1.x, p1.y);
        polygon3.addPoint(p2.x, p2.y);
        polygon3.addPoint(p6.x, p6.y);
        polygon3.addPoint(p5.x, p5.y);

        Polygon polygon4 = new Polygon(); // wall
        polygon4.addPoint(p2.x, p2.y);
        polygon4.addPoint(p3.x, p3.y);
        polygon4.addPoint(p7.x, p7.y);
        polygon4.addPoint(p6.x, p6.y);

        Polygon polygon5 = new Polygon(); // wall
        polygon5.addPoint(p3.x, p3.y);
        polygon5.addPoint(p4.x, p4.y);
        polygon5.addPoint(p8.x, p8.y);
        polygon5.addPoint(p7.x, p7.y);

        Polygon polygon6 = new Polygon(); // wall
        polygon6.addPoint(p4.x, p4.y);
        polygon6.addPoint(p1.x, p1.y);
        polygon6.addPoint(p5.x, p5.y);
        polygon6.addPoint(p8.x, p8.y);

        Graphics2D g2 = (Graphics2D) graphics;
        Stroke oldStroke = g2.getStroke();

        g2.setStroke(new BasicStroke(lineThickness));
        g2.setColor(outlineColor);
        g2.drawPolygon(polygon);
        g2.drawPolygon(polygon2);
        g2.drawPolygon(polygon3);
        g2.drawPolygon(polygon4);
        g2.drawPolygon(polygon5);
        g2.drawPolygon(polygon6);
        g2.setStroke(oldStroke);
        g2.setColor(fillColor);
        g2.fillPolygon(polygon);
        g2.fillPolygon(polygon2);
        g2.fillPolygon(polygon3);
        g2.fillPolygon(polygon4);
        g2.fillPolygon(polygon5);
        g2.fillPolygon(polygon6);
    }

    /**
     * Draws a dot on the minimap at the specified location.
     *
     * @param graphics graphics object on which the dot should be drawn
     * @param x center x coordinate of the dot
     * @param y center y coordinate of the dot
     */
    public static void drawMiniMapDot(Graphics graphics, int x, int y) {
        drawMiniMapDot(graphics, x, y, 9, Color.BLACK, Color.RED);
    }

    /**
     * Draws a dot on the minimap at the specified location with the specified color and diameter.
     *
     * @param graphics graphics object on which the dot should be drawn
     * @param x center x coordinate of the dot
     * @param y center y coordinate of the dot
     * @param diameter diameter of the dot
     * @param outlineColor the outlining color of the dot
     * @param fillColor the filling color of the dot
     */
    public static void drawMiniMapDot(Graphics graphics, int x, int y, int diameter, Color outlineColor, Color fillColor) {
        Point point = Perspective.worldToMiniMap(x, y);

        graphics.setColor(outlineColor);
        graphics.fillOval(point.x - 1 , point.y - 1, diameter, diameter);
        graphics.setColor(fillColor);
        graphics.fillOval(point.x, point.y, diameter - 2, diameter - 2);
    }
}
