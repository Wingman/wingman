package com.wingman.client.api.ui.settingscreen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsItem {

    private String description;

    private List<JComponent> components = new ArrayList<>();

    public SettingsItem(String description) {
        this.description = description;
    }

    public void add(JComponent... components) {
        if (components != null) {
            Collections.addAll(this.components, components);
        }
    }

    public void remove(JComponent... components) {
        if (components != null) {
            for (JComponent component : components) {
                this.components.remove(component);
            }
        }
    }

    public JPanel build() {
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS) {
            @Override
            public Dimension minimumLayoutSize(Container target) {
                return new Dimension(Integer.MAX_VALUE, 30);
            }

            @Override
            public Dimension preferredLayoutSize(Container target) {
                return new Dimension(Integer.MAX_VALUE, 30);
            }

            @Override
            public Dimension maximumLayoutSize(Container target) {
                return new Dimension(Integer.MAX_VALUE, 30);
            }
        });

        panel.add(Box.createHorizontalStrut(10));
        panel.add(new JLabel(description));
        panel.add(Box.createHorizontalGlue());

        for (JComponent component : components) {
            panel.add(component);
        }

        panel.add(Box.createHorizontalStrut(5));

        return panel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
