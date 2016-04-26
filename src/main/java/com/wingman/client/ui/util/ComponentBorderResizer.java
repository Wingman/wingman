package com.wingman.client.ui.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 *  The ComponentBorderResizer allows you to resize a component by dragging a border of the component.
 */
public class ComponentBorderResizer extends MouseAdapter {

    private static Map<Integer, Integer> cursors = new HashMap<>();
    {
        cursors.put(1, Cursor.N_RESIZE_CURSOR);
        cursors.put(2, Cursor.W_RESIZE_CURSOR);
        cursors.put(4, Cursor.S_RESIZE_CURSOR);
        cursors.put(8, Cursor.E_RESIZE_CURSOR);
        cursors.put(3, Cursor.NW_RESIZE_CURSOR);
        cursors.put(9, Cursor.NE_RESIZE_CURSOR);
        cursors.put(6, Cursor.SW_RESIZE_CURSOR);
        cursors.put(12, Cursor.SE_RESIZE_CURSOR);
    }

    private Insets dragInsets = new Insets(5, 5, 5, 5);
    private Dimension snapSize = new Dimension(1, 1);

    private int direction;
    private static final int NORTH = 1;
    private static final int WEST = 2;
    private static final int SOUTH = 4;
    private static final int EAST = 8;

    private Cursor sourceCursor;
    private boolean resizing;
    private Rectangle bounds;
    private Point pressed;
    private boolean autoScrolls;

    /**
     *  Convenience constructor. <br>
     *  All borders are resizable in increments of a single pixel. <br>
     *  Components must be registered separately.
     */
    public ComponentBorderResizer(Component... components) {
        for (Component component : components) {
            component.addMouseListener( this );
            component.addMouseMotionListener( this );
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Component source = e.getComponent();
        Point location = e.getPoint();
        direction = 0;

        if (location.x < dragInsets.left) {
            direction += WEST;
        }

        if (location.x > source.getWidth() - dragInsets.right - 1) {
            direction += EAST;
        }

        if (location.y < dragInsets.top) {
            direction += NORTH;
        }

        if (location.y > source.getHeight() - dragInsets.bottom - 1) {
            direction += SOUTH;
        }

        if (direction == 0) {
            source.setCursor(sourceCursor);
        } else {
            Cursor cursor = Cursor.getPredefinedCursor(cursors.get(direction));
            source.setCursor(cursor);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!resizing) {
            Component source = e.getComponent();
            sourceCursor = source.getCursor();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!resizing) {
            Component source = e.getComponent();
            source.setCursor(sourceCursor);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (direction == 0) {
            return;
        }

        resizing = true;

        Component source = e.getComponent();
        pressed = e.getPoint();
        SwingUtilities.convertPointToScreen(pressed, source);
        bounds = source.getBounds();

        if (source instanceof JComponent) {
            JComponent jc = (JComponent)source;
            autoScrolls = jc.getAutoscrolls();
            jc.setAutoscrolls(false);
        }
    }

    /**
     *  Restore the original state of the Component
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        resizing = false;

        Component source = e.getComponent();
        source.setCursor(sourceCursor);

        if (source instanceof JComponent) {
            ((JComponent)source).setAutoscrolls(autoScrolls);
        }
    }

    /**
     *  Resize the component ensuring location and size is within the bounds
     *  of the parent container and that the size is within the minimum and
     *  maximum constraints.
     *
     *  All calculations are done using the bounds of the component when the
     *  resizing started.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (!resizing) {
            return;
        }

        Component source = e.getComponent();
        Point dragged = e.getPoint();
        SwingUtilities.convertPointToScreen(dragged, source);

        changeBounds(source, direction, bounds, pressed, dragged);
    }

    private void changeBounds(Component source, int direction, Rectangle bounds, Point pressed, Point current) {
        Dimension minimumSize = source.getMinimumSize();
        Dimension maximumSize = source.getMaximumSize();

        int x = bounds.x;
        int y = bounds.y;
        int width = bounds.width;
        int height = bounds.height;

        if (WEST == (direction & WEST)) {
            int drag = getDragDistance(pressed.x, current.x, snapSize.width);
            int maximum = Math.min(width + x, maximumSize.width);
            drag = getDragBounded(drag, snapSize.width, width, minimumSize.width, maximum);

            x -= drag;
            width += drag;
        }

        if (NORTH == (direction & NORTH)) {
            int drag = getDragDistance(pressed.y, current.y, snapSize.height);
            int maximum = Math.min(height + y, maximumSize.height);
            drag = getDragBounded(drag, snapSize.height, height, minimumSize.height, maximum);

            y -= drag;
            height += drag;
        }

        if (EAST == (direction & EAST)) {
            int drag = getDragDistance(current.x, pressed.x, snapSize.width);
            Dimension boundingSize = getBoundingSize(source);
            int maximum = Math.min(boundingSize.width - x, maximumSize.width);
            drag = getDragBounded(drag, snapSize.width, width, minimumSize.width, maximum);
            width += drag;
        }

        if (SOUTH == (direction & SOUTH)) {
            int drag = getDragDistance(current.y, pressed.y, snapSize.height);
            Dimension boundingSize = getBoundingSize(source);
            int maximum = Math.min(boundingSize.height - y, maximumSize.height);
            drag = getDragBounded(drag, snapSize.height, height, minimumSize.height, maximum);
            height += drag;
        }

        source.setBounds(x, y, width, height);
        source.validate();
    }

    /**
     *  Determines how far the mouse has moved from where dragging started.
     */
    private int getDragDistance(int larger, int smaller, int snapSize) {
        int halfway = snapSize / 2;
        int drag = larger - smaller;
        drag += (drag < 0) ? -halfway : halfway;
        drag = (drag / snapSize) * snapSize;
        return drag;
    }

    /**
     *  Adjusts the drag value to be within the minimum and maximum range.
     */
    private int getDragBounded(int drag, int snapSize, int dimension, int minimum, int maximum) {
        while (dimension + drag < minimum) {
            drag += snapSize;
        }
        while (dimension + drag > maximum) {
            drag -= snapSize;
        }
        return drag;
    }

    /**
     *  Keeps the size of the component within the bounds of its parent.
     */
    private Dimension getBoundingSize(Component source) {
        if (source instanceof Window) {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Rectangle bounds = env.getMaximumWindowBounds();
            return new Dimension(bounds.width, bounds.height);
        } else {
            return source.getParent().getSize();
        }
    }
}
