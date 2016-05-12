package com.wingman.client.ui.style;

import com.wingman.client.Util;

import javax.swing.*;
import javax.swing.plaf.synth.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.IOException;

public class OnyxStyleFactory extends SynthStyleFactory {

    public static final Color VERY_DARK_BLACK = new Color(15, 15, 15);
    public static final Color DARK_BLACK = new Color(21, 21, 21);
    public static final Color VERY_LIGHT_WHITE = new Color(216, 216, 216);
    public static final Color LIGHT_BROWN = new Color(199, 173, 129);
    public static final Color LIGHT_BLUE = new Color(0, 157, 230);
    public static final Color DARK_GRAY = new Color(91, 89, 82);

    private static Font defaultFont = new Font("Helvetica", Font.PLAIN, 12);

    private static SynthStyle defaultStyle = new SynthStyle() {
        @Override
        protected Color getColorForState(SynthContext context, ColorType type) {
            JComponent component = context.getComponent();
            if (type == ColorType.FOREGROUND
                    || type == ColorType.TEXT_FOREGROUND) {
                Color contextForeground = component.getForeground();
                if (contextForeground == null) {
                    return VERY_LIGHT_WHITE;
                }
                return contextForeground;
            } else if (type == ColorType.BACKGROUND
                    || type == ColorType.TEXT_BACKGROUND) {
                Color contextBackground = component.getBackground();
                if (contextBackground == null) {
                    if (component instanceof JPanel) {
                        return DARK_BLACK;
                    } else if (component instanceof JLabel) {
                        if (component.getParent() != null) {
                            return component.getParent().getBackground();
                        }
                    }
                    return VERY_DARK_BLACK;
                }
                return contextBackground;
            }
            return DARK_BLACK;
        }

        @Override
        protected Font getFontForState(SynthContext context) {
            Font contextFont = context.getComponent().getFont();
            if (contextFont != null) {
                return contextFont;
            }
            return defaultFont;
        }
    };

    @Override
    public SynthStyle getStyle(JComponent c, Region id) {
        if (c instanceof JLabel) {
            c.setOpaque(false);
        } else if (c instanceof JButton) {
            c.setOpaque(false);
        } else if (c instanceof JCheckBox) {
            try {
                ((JCheckBox) c).setRolloverEnabled(false);
                ((JCheckBox) c).setIcon(new ImageIcon(Util.getFileAsBytes("/images/icons/unchecked.png")));
                ((JCheckBox) c).setSelectedIcon(new ImageIcon(Util.getFileAsBytes("/images/icons/checked.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (c instanceof JTextComponent) {
            JComponent parent = (JComponent) c.getParent();
            if (parent != null) {
                if (parent.getBackground() == VERY_DARK_BLACK) {
                    c.setBackground(DARK_BLACK);
                    c.setBorder(BorderFactory.createLineBorder(DARK_GRAY));
                } else if (parent.getBackground() == DARK_BLACK) {
                    c.setBackground(VERY_DARK_BLACK);
                    c.setBorder(BorderFactory.createLineBorder(DARK_GRAY));
                }
            }
        }
        return defaultStyle;
    }
}
