package com.wingman.client.ui.titlebars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class OnyxTitleBar extends JMenuBar {

    private Point initialClick;

    public OnyxTitleBar(final Component parent) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS) {
            @Override
            public Dimension minimumLayoutSize(Container target) {
                return new Dimension(0, 24);
            }

            @Override
            public Dimension preferredLayoutSize(Container target) {
                return new Dimension(0, 24);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = parent.getLocation().x;
                int thisY = parent.getLocation().y;

                int xMoved = thisX + e.getX() - (thisX + initialClick.x);
                int yMoved = thisY + e.getY() - (thisY + initialClick.y);

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                parent.setLocation(X, Y);
            }
        });
    }
}
