package com.wingman.client.ui.components;

import com.wingman.client.ui.style.OnyxStyleFactory;

import javax.swing.*;
import java.awt.*;

public class ShadowLabel extends JLabel {

    public ShadowLabel() {
        super();
    }

    public ShadowLabel(String text) {
        super(text);
    }

    private int leftX;
    private int leftY;

    private int rightX = 1;
    private int rightY = 1;

    private Color leftColor = OnyxStyleFactory.DARK_BLACK;
    private Color rightColor = OnyxStyleFactory.DARK_BLACK;

    public void setLeftShadow(int x, int y, Color color) {
        leftX = x;
        leftY = y;
        leftColor = color;
    }

    public void setRightShadow(int x, int y, Color color) {
        rightX = x;
        rightY = y;
        rightColor = color;
    }

    public Dimension getPreferredSize() {
        FontMetrics fontMetrics = getFontMetrics(getFont());

        int width = fontMetrics.stringWidth(getText());
        width += leftX + rightX;

        int height = fontMetrics.getHeight();
        height += leftY + rightY;

        return new Dimension(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setFont(getFont());

        ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        FontMetrics fontMetrics = this.getFontMetrics(getFont());

        int x = 0;
        int height = fontMetrics.getAscent();

        char[] chars = getText().toCharArray();

        for(int i = 0; i < chars.length; i++) {
            char character = chars[i];

            g.setColor(leftColor);
            g.drawString("" + character, x - leftX, height - leftY);

            g.setColor(rightColor);
            g.drawString("" + character, x + rightX, height + rightY);

            g.setColor(getForeground());
            g.drawString("" + character, x, height);

            x += fontMetrics.charWidth(character);
        }

        ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);

    }
}
