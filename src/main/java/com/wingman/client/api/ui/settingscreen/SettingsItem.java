package com.wingman.client.api.ui.settingscreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsItem {

    private final String description;

    public List<JComponent> components = new ArrayList<>();

    public SettingsItem(String description) {
        this.description = description;
    }

    public void add(JComponent... components) {
        if (components != null) {
            Collections.addAll(this.components, components);
        }
    }

    public JPanel build() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel descriptionLabel = new JLabel(getDescription());
        descriptionLabel.setBorder(new EmptyBorder(7, 10, 7, 0));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(new EmptyBorder(7, 0, 7, 20));
        rightPanel.setOpaque(false);

        for (JComponent component : components) {
            rightPanel.add(component);
        }

        panel.add(descriptionLabel);
        panel.add(Box.createHorizontalGlue());
        panel.add(rightPanel);

        return panel;
    }

    public String getDescription() {
        return description;
    }
}
