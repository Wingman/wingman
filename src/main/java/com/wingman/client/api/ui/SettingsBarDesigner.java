package com.wingman.client.api.ui;

import com.wingman.client.ui.style.OnyxSkin;

import javax.swing.*;
import java.util.ArrayList;


/**
 * {@link SettingsBarDesigner} provides methods that could be in help when creating a {@link SettingsBar}. <br>
 * It is not at all required to use this class to design settings, but it is recommended.
 */
public class SettingsBarDesigner {

    public static SettingsBar designSettingsBar(SettingsBar currentSettingsBar, ArrayList<ArrayList<JComponent>> settings, boolean alternateColor) {
        for (int i = 0; i < settings.size(); i++) {
            JPanel panel = createSettingsEntry(settings.get(i));
            if (alternateColor) {
                if (i % 2 == 1) {
                    panel.setBackground(OnyxSkin.VERY_DARK_BLACK);
                }
            }
            currentSettingsBar.panel.add(panel);
        }

        return currentSettingsBar;
    }

    public static JPanel createSettingsEntry(ArrayList<JComponent> components) {
        return createSettingsEntry(components.toArray(new JComponent[components.size()]));
    }

    public static JPanel createSettingsEntry(JComponent... components) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalStrut(5));

        for (int i = 0; i < components.length; i++) {
            panel.add(components[i]);
            if (i == 0) {
                panel.add(Box.createHorizontalGlue());
            }
        }

        return panel;
    }
}
