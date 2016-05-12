package com.wingman.client.api.ui;

import com.wingman.client.ui.style.OnyxStyleFactory;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;


/**
 * {@link SettingsBarDesigner} provides methods that could be in help when creating a {@link SettingsBar}. <br>
 * It is not at all required to use this class to design settings, but it is useful for consistency across different plugins.
 */
public class SettingsBarDesigner {

    public static JPanel createSettingsRow(String description, Object jComponent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS) {
            @Override
            public Dimension maximumLayoutSize(Container target) {
                return new Dimension(700, 20);
            }
        });
        panel.add(Box.createHorizontalStrut(5));
        panel.add(new JLabel(description));
        panel.add(Box.createHorizontalGlue());
        if (((JComponent) jComponent).getInsets() == null) {
            if (jComponent instanceof AbstractButton) {
                ((AbstractButton) jComponent).setMargin(new Insets(0, 10, 0, 5));
            } else  if (jComponent instanceof JTextComponent) {
                ((JTextComponent) jComponent).setMargin(new Insets(0, 10, 0, 5));
            }
        }
        panel.add((JComponent) jComponent);
        return panel;
    }

    public static SettingsBar designSettingsBar(SettingsBar currentSettingsBar, ArrayList<JPanel> settingPanels, boolean alternateColor) {
        int i = 0;
        for (JPanel setting : settingPanels) {
            if (alternateColor) {
                if (i++ % 2 == 1) {
                    setting.setBackground(OnyxStyleFactory.VERY_DARK_BLACK);
                }
            }
            currentSettingsBar.panel.add(setting);
        }
        return currentSettingsBar;
    }
}
