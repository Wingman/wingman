package com.wingman.client.ui.style;

import com.wingman.client.ui.style.synthstyles.ComboBoxStyle;
import com.wingman.client.ui.style.synthstyles.PanelStyle;
import com.wingman.client.ui.style.synthstyles.TextFieldStyle;
import com.wingman.client.util.FileUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.synth.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.IOException;

public class OnyxStyleFactory extends SynthStyleFactory {

    public static final Color BASE = Color.decode("#3a3a3a");
    public static final Color BASE_DARKER = BASE.darker();
    public static final Color BASE_BRIGHTER = BASE.brighter();
    public static final Color BASE_BRIGHTEST = BASE_BRIGHTER.brighter();

    public static final Color BASE_BLUE = Color.decode("#22A7F0");
    public static final Color BASE_BLUE_DARKER = BASE_BLUE.darker();

    public static final Color PRIMARY_TEXT_COLOR = Color.decode("#F5F5F5");
    public static final Color SECONDARY_TEXT_COLOR = Color.decode("#E0E0E0");

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    public static final Font ROBOTO_REGULAR = new Font("Roboto", Font.PLAIN, 12);
    public static final Font ROBOTO_MEDIUM = new Font("Roboto Medium", Font.PLAIN, 12);

    private static final int TEXT_COMPONENT_BORDER_SIZE = 3;

    private static SynthStyle textFieldStyle = new TextFieldStyle();
    private static SynthStyle panelStyle = new PanelStyle();
    private static SynthStyle comboBoxStyle = new ComboBoxStyle();

    private ImageIcon checkBoxIcon;
    private ImageIcon checkBoxIconChecked;

    public OnyxStyleFactory() {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            graphicsEnvironment.registerFont(Font
                    .createFont(Font.TRUETYPE_FONT, FileUtil.getFile("/fonts/Roboto-Regular.ttf")));
            graphicsEnvironment.registerFont(Font
                    .createFont(Font.TRUETYPE_FONT, FileUtil.getFile("/fonts/Roboto-Medium.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        try {
            checkBoxIcon = new ImageIcon(FileUtil.getFileAsBytes("/images/icons/unchecked.png"));
            checkBoxIconChecked = new ImageIcon(FileUtil.getFileAsBytes("/images/icons/checked.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SynthStyle defaultStyle = new SynthStyle() {
        @Override
        protected Color getColorForState(SynthContext context, ColorType type) {
            if (type == ColorType.FOREGROUND || type == ColorType.TEXT_FOREGROUND) {
                return PRIMARY_TEXT_COLOR;
            }
            return BASE;
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
        if (id == Region.PANEL) {
            return panelStyle;
        } else if (id == Region.BUTTON) {
            c.setOpaque(false);
        } else if (id == Region.LABEL) {
            Color background = c.getBackground();
            if (background == null || background instanceof UIResource) {
                c.setOpaque(false);
            }
        }

        if (c instanceof JTextComponent) {
            JTextComponent textComponent = (JTextComponent) c;

            Border border = textComponent.getBorder();
            if (border == null || border instanceof UIResource) {
                textComponent.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(BASE_BRIGHTER, 1),
                        new EmptyBorder(TEXT_COMPONENT_BORDER_SIZE,
                                TEXT_COMPONENT_BORDER_SIZE,
                                TEXT_COMPONENT_BORDER_SIZE,
                                TEXT_COMPONENT_BORDER_SIZE)
                ));
            }

            Color selectionColor = textComponent.getSelectionColor();
            if (selectionColor == null || selectionColor instanceof UIResource) {
                textComponent.setSelectionColor(BASE_BLUE_DARKER);
            }

            Color selectedTextColor = textComponent.getSelectedTextColor();
            if (selectedTextColor == null || selectedTextColor instanceof UIResource) {
                textComponent.setSelectedTextColor(SECONDARY_TEXT_COLOR);
            }

            return textFieldStyle;
        }

        if (id == Region.CHECK_BOX) {
            JCheckBox checkBox = (JCheckBox) c;

            checkBox.setRolloverEnabled(false);
            checkBox.setIcon(checkBoxIcon);
            checkBox.setSelectedIcon(checkBoxIconChecked);
        } else if (id == Region.COMBO_BOX) {
            if (c.getBorder() == null) {
                c.setBorder(BorderFactory.createLineBorder(OnyxStyleFactory.BASE_BRIGHTER));
            }

            return comboBoxStyle;
        } else if (id == Region.PROGRESS_BAR) {
            Color foreground = c.getForeground();
            if (foreground == null || foreground instanceof UIResource) {
                c.setForeground(BASE_BLUE);
            }
        }

        return defaultStyle;

        /*if (c instanceof JLabel) {
            c.setOpaque(false);
        } else if (c instanceof JButton) {
            c.setOpaque(false);
            if (c.getBorder() == null) {
                c.setBorder(BorderFactory.createLineBorder(BASE));
            }
        } else if (c instanceof JCheckBox) {
            if (c.getBorder() == null) {
                c.setBorder(BorderFactory.createLineBorder(PRIMARY_DARK));
            }
            try {
                ((JCheckBox) c).setRolloverEnabled(false);
                ((JCheckBox) c).setIcon(new ImageIcon(FileUtil.getFileAsBytes("/images/icons/unchecked.png")));
                ((JCheckBox) c).setSelectedIcon(new ImageIcon(FileUtil.getFileAsBytes("/images/icons/checked.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (c instanceof JTextComponent) {
            if (c.getBorder() == null) {
                ((JTextComponent) c).setSelectionColor(PRIMARY_TEXT_COLOR);
                ((JTextComponent) c).setSelectedTextColor(BASE);
                JComponent parent = (JComponent) c.getParent();
                if (parent != null) {
                    if (parent.getBackground() == BASE) {
                        c.setBackground(MID_BLACK);
                        c.setBorder(BorderFactory.createLineBorder(PRIMARY_DARK));
                    } else if (parent.getBackground() == MID_BLACK) {
                        c.setBackground(BASE);
                        c.setBorder(BorderFactory.createLineBorder(PRIMARY_DARK));
                    }
                }
            }
        } else if (c instanceof JList) {
            if (c.getBackground() == null) {
                c.setBackground(MID_BLACK);
            }
        } else if (c instanceof JSeparator) {
            c.setBackground(OnyxStyleFactory.PRIMARY_DARK);
        } else if(c instanceof JProgressBar){
            c.setForeground(LIGHT_BLUE);
        }*/
    }
}
