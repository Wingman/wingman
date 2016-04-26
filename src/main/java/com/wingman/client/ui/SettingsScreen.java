package com.wingman.client.ui;

import com.wingman.client.api.ui.SettingsBar;
import com.wingman.client.ui.style.OnyxSkin;
import com.wingman.client.ui.titlebars.SettingsTitleBar;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.HashMap;
import java.util.Map;

public class SettingsScreen extends JDialog {

    public Map<String, SettingsBar> settingsBars = new HashMap<>();

    public DefaultListModel<String> buttonListModel = new DefaultListModel<>();
    public final JList<String> buttonList = new JList<>(buttonListModel);

    public JPanel settingsBarPanel = new JPanel();

    public SettingsScreen() {
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, OnyxSkin.VERY_DARK_BLACK));
        this.setUndecorated(true);
        this.setJMenuBar(new SettingsTitleBar(this));
        this.setAlwaysOnTop(true);
        this.setResizable(false);

        settingsBarPanel.setLayout(new BoxLayout(settingsBarPanel, BoxLayout.Y_AXIS));

        buttonList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                SettingsBar selectedSettingsBar = settingsBars.get(buttonList.getSelectedValue());
                if (selectedSettingsBar != null) {
                    settingsBarPanel.removeAll();
                    settingsBarPanel.add(selectedSettingsBar.panel);
                    Client.settingsScreen.pack();
                }
            }
        });
        buttonList.setBackground(OnyxSkin.VERY_DARK_BLACK);

        JScrollPane buttonScrollPane = new JScrollPane();
        buttonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        buttonScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        buttonScrollPane.setBackground(OnyxSkin.VERY_DARK_BLACK);
        buttonScrollPane.getViewport().add(buttonList);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(buttonScrollPane);
        panel.add(settingsBarPanel);

        this.setContentPane(panel);
    }
}
