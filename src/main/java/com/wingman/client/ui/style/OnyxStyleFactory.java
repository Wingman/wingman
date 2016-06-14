package com.wingman.client.ui.style;

import com.wingman.client.Util;

import javax.swing.*;
import javax.swing.plaf.synth.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.IOException;

public class OnyxStyleFactory extends SynthStyleFactory {

    public static final Color DARK_BLACK = new Color(15, 15, 15);
    public static final Color MID_BLACK = new Color(21, 21, 21);
    public static final Color LIGHT_BLACK = new Color(30, 30, 30);

    public static final Color LIGHT_WHITE = new Color(216, 216, 216);

    public static final Color DARK_GRAY = new Color(91, 89, 82);
    public static final Color LIGHT_GRAY = new Color(180, 180, 185);

    public static final Color LIGHT_BROWN = new Color(199, 173, 129);

    public static final Color LIGHT_BLUE = new Color(34, 140, 219);

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    public static final Font ROBOTO_REGULAR = new Font("Roboto", Font.PLAIN, 12);
    public static final Font ROBOTO_MEDIUM = new Font("Roboto Medium", Font.PLAIN, 12);

    public OnyxStyleFactory() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            graphicsEnvironment.registerFont(Font.createFont(Font.TRUETYPE_FONT, Util.getFile("/fonts/Roboto-Regular.ttf")));
            graphicsEnvironment.registerFont(Font.createFont(Font.TRUETYPE_FONT, Util.getFile("/fonts/Roboto-Medium.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private static SynthStyle defaultStyle = new SynthStyle() {
        @Override
        protected Color getColorForState(SynthContext context, ColorType type) {
            JComponent component = context.getComponent();
            if (type == ColorType.FOREGROUND
                    || type == ColorType.TEXT_FOREGROUND) {
                Color contextForeground = component.getForeground();
                if (contextForeground == null) {
                    return Color.WHITE;
                }
                return contextForeground;
            } else if (type == ColorType.BACKGROUND
                    || type == ColorType.TEXT_BACKGROUND) {
                Color contextBackground = component.getBackground();
                if (contextBackground == null) {
                    if (component instanceof JPanel) {
                        return MID_BLACK;
                    } else if (component instanceof JLabel) {
                        if (component.getParent() != null) {
                            return component.getParent().getBackground();
                        }
                    }
                    return DARK_BLACK;
                }
                return contextBackground;
            }
            return MID_BLACK;
        }

        @Override
        protected Font getFontForState(SynthContext context) {
            Font contextFont = context.getComponent().getFont();
            if (contextFont != null) {
                return contextFont;
            }
            return ROBOTO_REGULAR;
        }
    };

    @Override
    public SynthStyle getStyle(JComponent c, Region id) {
        if (c instanceof JLabel) {
            c.setOpaque(false);
        } else if (c instanceof JButton) {
            c.setOpaque(false);
            if (c.getBorder() == null) {
                c.setBorder(BorderFactory.createLineBorder(DARK_BLACK));
            }
        } else if (c instanceof JCheckBox) {
            if (c.getBorder() == null) {
                c.setBorder(BorderFactory.createLineBorder(LIGHT_BLACK));
            }
            try {
                ((JCheckBox) c).setRolloverEnabled(false);
                ((JCheckBox) c).setIcon(new ImageIcon(Util.getFileAsBytes("/images/icons/unchecked.png")));
                ((JCheckBox) c).setSelectedIcon(new ImageIcon(Util.getFileAsBytes("/images/icons/checked.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (c instanceof JTextComponent) {
            if (c.getBorder() == null) {
                ((JTextComponent) c).setSelectionColor(LIGHT_WHITE);
                ((JTextComponent) c).setSelectedTextColor(DARK_BLACK);
                JComponent parent = (JComponent) c.getParent();
                if (parent != null) {
                    if (parent.getBackground() == DARK_BLACK) {
                        c.setBackground(MID_BLACK);
                        c.setBorder(BorderFactory.createLineBorder(LIGHT_BLACK));
                    } else if (parent.getBackground() == MID_BLACK) {
                        c.setBackground(DARK_BLACK);
                        c.setBorder(BorderFactory.createLineBorder(LIGHT_BLACK));
                    }
                }
            }
        } else if (c instanceof JList) {
            if (c.getBackground() == null) {
                c.setBackground(MID_BLACK);
            }
        } else if (c instanceof JSeparator) {
            c.setBackground(OnyxStyleFactory.LIGHT_BLACK);
        }
        return defaultStyle;
    }
}
