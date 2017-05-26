package com.wingman.client.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverButton extends JButton {

    private float opacity = 0.5F;
    private boolean hovering;

    public HoverButton(ImageIcon imageIcon) {
        this();
        this.setIcon(imageIcon);
        this.setBorderPainted(false);
    }

    public HoverButton() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                super.mouseExited(e);
            }
        });
        this.setFocusPainted(false);
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    @Override
    public void paint(Graphics g) {
        if (hovering) {
            super.paint(g);
        } else {
            applyOpacity(g);
        }
    }

    public void applyOpacity(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        super.paint(g);
        g2.dispose();
    }
}
