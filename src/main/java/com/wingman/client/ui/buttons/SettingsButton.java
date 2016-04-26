package com.wingman.client.ui.buttons;

import com.google.common.base.Throwables;
import com.wingman.client.Util;
import com.wingman.client.ui.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SettingsButton extends HoverButton {

    public SettingsButton() {
        try {
            this.setIcon(new ImageIcon(ImageIO.read(Util.getFile("/images/icons/settings.png"))));
        } catch (IOException e) {
            Throwables.propagate(e);
        }
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setBorder(null);
        this.setFocusPainted(false);

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Client.settingsScreen.isVisible()) {
                    Client.settingsScreen.setVisible(false);
                } else {
                    Client.settingsScreen.setVisible(true);
                }
            }
        });

        this.setMaximumSize(new Dimension(16, 16));
    }
}
