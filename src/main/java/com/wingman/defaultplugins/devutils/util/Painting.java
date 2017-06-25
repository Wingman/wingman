package com.wingman.defaultplugins.devutils.util;

import com.wingman.defaultplugins.devutils.game.world.Perspective;

import java.awt.*;
import java.util.Optional;

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
     * @return {@code true} if the overlay was drawn;
     *         {@code false} otherwise
     */
    public static boolean drawTileOverlay(Graphics graphics, int x, int y, int zOffset, int plane, Color outlineColor, Color fillColor, float lineThickness) {
        Optional<Point> optionalP1 = Perspective.worldToScreen(x - 64, y - 64, plane, zOffset);
        Optional<Point> optionalP2 = Perspective.worldToScreen(x - 64, y + 64, plane, zOffset);
        Optional<Point> optionalP3 = Perspective.worldToScreen(x + 64, y + 64, plane, zOffset);
        Optional<Point> optionalP4 = Perspective.worldToScreen(x + 64, y - 64, plane, zOffset);

        if (optionalP1.isPresent() && optionalP2.isPresent()
                && optionalP3.isPresent() && optionalP4.isPresent()) {

            Point p1 = optionalP1.get();
            Point p2 = optionalP2.get();
            Point p3 = optionalP3.get();
            Point p4 = optionalP4.get();

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

            return true;
        }

        return false;
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
     * @return {@code true} if the overlay was drawn;
     *         {@code false} otherwise
     */
    public static boolean drawTileOverlayBox(Graphics graphics, int x, int y, int zOffset, int plane, int height, Color outlineColor, Color fillColor, float lineThickness) {
        Optional<Point> optionalP1 = Perspective.worldToScreen(x - 64, y - 64, plane, zOffset);
        Optional<Point> optionalP2 = Perspective.worldToScreen(x - 64, y + 64, plane, zOffset);
        Optional<Point> optionalP3 = Perspective.worldToScreen(x + 64, y + 64, plane, zOffset);
        Optional<Point> optionalP4 = Perspective.worldToScreen(x + 64, y - 64, plane, zOffset);

        Optional<Point> optionalP5 = Perspective.worldToScreen(x - 64, y - 64, plane, zOffset + height);
        Optional<Point> optionalP6 = Perspective.worldToScreen(x - 64, y + 64, plane, zOffset + height);
        Optional<Point> optionalP7 = Perspective.worldToScreen(x + 64, y + 64, plane, zOffset + height);
        Optional<Point> optionalP8 = Perspective.worldToScreen(x + 64, y - 64, plane, zOffset + height);

        if (optionalP1.isPresent() && optionalP2.isPresent()
                && optionalP3.isPresent() && optionalP4.isPresent()
                && optionalP5.isPresent() && optionalP6.isPresent()
                && optionalP7.isPresent() && optionalP8.isPresent()) {

            Point p1 = optionalP1.get();
            Point p2 = optionalP2.get();
            Point p3 = optionalP3.get();
            Point p4 = optionalP4.get();
            Point p5 = optionalP5.get();
            Point p6 = optionalP6.get();
            Point p7 = optionalP7.get();
            Point p8 = optionalP8.get();

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

            return true;
        }

        return false;
    }
}
