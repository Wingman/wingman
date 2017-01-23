package com.wingman.client.ui;

import com.wingman.client.util.FileUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ClientTrayIcon extends TrayIcon {

    public PopupMenu popup;
    public SystemTray tray;

    public ClientTrayIcon() throws IOException, AWTException {
        super(ImageIO.read(FileUtil.getFile("/images/icons/icon_16x16.png")));
        if (SystemTray.isSupported()) {
            popup = new PopupMenu();

            MenuItem exitItem = new MenuItem("Quit");
            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Client.frame.dispatchEvent(new WindowEvent(Client.frame, WindowEvent.WINDOW_CLOSING));
                }
            });
            popup.add(exitItem);

            this.setPopupMenu(popup);

            tray = SystemTray.getSystemTray();
            tray.add(this);
        }
    }

    public void detach() {
        if (tray != null) {
            tray.remove(this);
        }
    }
}
