package com.wingman.client.ui;

import com.google.common.base.Throwables;
import com.wingman.client.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Tray {

    public PopupMenu popup = new PopupMenu();
    public SystemTray tray;
    public TrayIcon icon;

    public Tray() {
        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();

            try {
                icon = new TrayIcon(ImageIO.read(Util.getFile("/images/icons/icon_16x16.png")));

                MenuItem exitItem = new MenuItem("Quit");
                exitItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Client.frame.dispatchEvent(new WindowEvent(Client.frame, WindowEvent.WINDOW_CLOSING));
                    }
                });
                popup.add(exitItem);

                icon.setPopupMenu(popup);

                tray.add(icon);
            } catch (AWTException | IOException e) {
                Throwables.propagate(e);
            }
        }
    }

    public void detach() {
        if (tray != null) {
            if (icon != null) {
                tray.remove(icon);
            }
        }
    }
}
