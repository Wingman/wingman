package com.wingman.client.api.ui;

import com.wingman.client.ui.Client;

import javax.swing.*;

public class SettingsSection {

    public JPanel panel;
    public String sideText;

    public SettingsSection() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }

    public void register() {
        if (sideText != null) {
            Client.settingsScreen.settingsBars.put(sideText, this);
            Client.settingsScreen.buttonListModel.addElement(sideText);

            if (Client.settingsScreen.settingsBarPanel.getComponents().length == 0) {
                Client.settingsScreen.settingsBarPanel.add(panel);
                Client.settingsScreen.buttonList.setSelectedIndex(0);
                Client.settingsScreen.pack();
                Client.settingsScreen.setLocationRelativeTo(null);
            }
        }
    }

    public void unregister() {
        if (sideText != null) {
            Client.settingsScreen.settingsBars.remove(sideText);
            Client.settingsScreen.buttonListModel.removeElement(sideText);
            Client.settingsScreen.settingsBarPanel.remove(panel);
            Client.settingsScreen.settingsBarPanel.revalidate();
            Client.settingsScreen.settingsBarPanel.repaint();
        }
    }
}
