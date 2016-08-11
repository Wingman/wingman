package com.wingman.client.api.ui.settingscreen;

import com.wingman.client.api.plugin.Plugin;
import com.wingman.client.ui.style.OnyxStyleFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsSection {

    private final String owner;
    private final String description;
    private final String pluginVersion;
    private final String pluginId;

    private final SettingSectionToggleListener toggleListener;
    private final boolean defaultToggleState;

    public List<SettingsItem> items = new ArrayList<>();

    private JPanel builtListHeader;
    private JPanel builtSelectedHeader;

    /*
        Plugin specific constructors
     */

    public SettingsSection(Plugin plugin) {
        this(plugin, null, false);
    }

    public SettingsSection(Plugin plugin,
                           SettingSectionToggleListener toggleListener,
                           boolean defaultToggleState) {

        this(plugin.name(), plugin.description(), plugin, toggleListener, defaultToggleState);
    }

    public SettingsSection(String owner,
                           String description,
                           Plugin plugin) {

        this(owner, description, plugin, null, false);
    }

    public SettingsSection(String owner,
                           String description,
                           Plugin plugin,
                           SettingSectionToggleListener toggleListener,
                           boolean defaultToggleState) {

        this(owner, description, plugin.version(), plugin.id(), toggleListener, defaultToggleState);
    }

    /*
        General constructors
     */

    public SettingsSection(String owner, String description) {
        this(owner, description, null, false);
    }

    public SettingsSection(String owner,
                           String description,
                           SettingSectionToggleListener toggleListener,
                           boolean defaultToggleState) {

        this(owner, description, null, null, toggleListener, defaultToggleState);
    }

    private SettingsSection(String owner,
                           String description,
                           String pluginVersion,
                           String pluginId,
                           SettingSectionToggleListener toggleListener,
                           boolean defaultToggleState) {

        this.owner = owner;
        this.description = description;
        this.pluginVersion = pluginVersion;
        this.pluginId = pluginId;
        this.toggleListener = toggleListener;
        this.defaultToggleState = defaultToggleState;
    }

    public void add(SettingsItem... items) {
        if (items != null) {
            Collections.addAll(this.items, items);
        }
    }

    public JPanel buildSectionListHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JLabel ownerLabel = new JLabel(getOwner());
        ownerLabel.setFont(OnyxStyleFactory.ROBOTO_MEDIUM);
        ownerLabel.setBorder(new EmptyBorder(6, 9, 5, 0));

        JLabel descLabel = new JLabel(getDescription());
        descLabel.setForeground(OnyxStyleFactory.SECONDARY_TEXT_COLOR);
        descLabel.setBorder(new EmptyBorder(0, 9, 6, 0));

        leftPanel.add(ownerLabel);
        leftPanel.add(descLabel);

        panel.add(leftPanel);
        panel.add(Box.createHorizontalGlue());

        if (pluginId != null
                && pluginVersion != null) {

            JPanel pluginPanel = new JPanel();
            pluginPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
            pluginPanel.setLayout(new BoxLayout(pluginPanel, BoxLayout.Y_AXIS));
            pluginPanel.setOpaque(false);

            JLabel pluginVersionLabel = new JLabel("Version " + pluginVersion);
            pluginVersionLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            pluginVersionLabel.setForeground(OnyxStyleFactory.SECONDARY_TEXT_COLOR);
            pluginVersionLabel.setBorder(new EmptyBorder(6, 9, 5, 0));

            JLabel pluginIdLabel = new JLabel(pluginId);
            pluginIdLabel.setFont(OnyxStyleFactory.ROBOTO_REGULAR.deriveFont(10f));
            pluginIdLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            pluginIdLabel.setForeground(OnyxStyleFactory.SECONDARY_TEXT_COLOR);
            pluginIdLabel.setBorder(new EmptyBorder(0, 9, 6, 0));

            pluginPanel.add(pluginVersionLabel);
            pluginPanel.add(pluginIdLabel);

            panel.add(pluginPanel);
        }

        if (toggleListener != null) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected(defaultToggleState);
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    toggleListener.toggled(e.getStateChange() == ItemEvent.SELECTED);
                }
            });
            checkBox.setBorder(new EmptyBorder(10, 10, 10, 20));
            checkBox.setOpaque(false);

            panel.add(checkBox);
        }

        return builtListHeader = panel;
    }

    public JPanel buildSelectedSectionHeader() {
        JPanel panel = new JPanel();
        panel.setBackground(OnyxStyleFactory.BASE_DARKER);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JLabel ownerLabel = new JLabel(getOwner());
        ownerLabel.setFont(OnyxStyleFactory.ROBOTO_MEDIUM);
        ownerLabel.setBorder(new EmptyBorder(6, 9, 5, 0));

        JLabel descLabel = new JLabel(getDescription());
        descLabel.setForeground(OnyxStyleFactory.SECONDARY_TEXT_COLOR);
        descLabel.setBorder(new EmptyBorder(0, 9, 6, 0));

        leftPanel.add(ownerLabel);
        leftPanel.add(descLabel);

        panel.add(leftPanel);
        panel.add(Box.createHorizontalGlue());

        if (pluginId != null
                && pluginVersion != null) {

            JPanel pluginPanel = new JPanel();
            pluginPanel.setBorder(new EmptyBorder(0, 0, 0, 10));
            pluginPanel.setLayout(new BoxLayout(pluginPanel, BoxLayout.Y_AXIS));
            pluginPanel.setOpaque(false);

            JLabel pluginVersionLabel = new JLabel("Version " + pluginVersion);
            pluginVersionLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            pluginVersionLabel.setForeground(OnyxStyleFactory.SECONDARY_TEXT_COLOR);
            pluginVersionLabel.setBorder(new EmptyBorder(6, 9, 5, 0));

            JLabel pluginIdLabel = new JLabel(pluginId);
            pluginIdLabel.setFont(OnyxStyleFactory.ROBOTO_REGULAR.deriveFont(10f));
            pluginIdLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            pluginIdLabel.setForeground(OnyxStyleFactory.SECONDARY_TEXT_COLOR);
            pluginIdLabel.setBorder(new EmptyBorder(0, 9, 6, 0));

            pluginPanel.add(pluginVersionLabel);
            pluginPanel.add(pluginIdLabel);

            panel.add(pluginPanel);
        }

        if (toggleListener != null) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected(defaultToggleState);
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    toggleListener.toggled(e.getStateChange() == ItemEvent.SELECTED);
                }
            });
            checkBox.setBorder(new EmptyBorder(10, 10, 10, 20));
            checkBox.setOpaque(false);

            panel.add(checkBox);
        }

        return builtSelectedHeader = panel;
    }

    public JScrollPane buildSelectedSectionBody() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        int i = 0;
        for (SettingsItem item : items) {
            JPanel itemPanel = item.build();

            if (i++ % 2 == 1) {
                itemPanel.setBackground(OnyxStyleFactory.BASE_DARKER);
            }

            panel.add(itemPanel);
        }

        return new JScrollPane(panel);
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public String getPluginId() {
        return pluginId;
    }

    public JPanel getListHeader() {
        return builtListHeader;
    }

    public JPanel getSelectedHeader() {
        return builtSelectedHeader;
    }
}
