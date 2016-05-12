package com.wingman.client.ui.titlebars;

import com.google.common.base.Throwables;
import com.wingman.client.Util;
import com.wingman.client.plugin.PluginManager;
import com.wingman.client.ui.Client;
import com.wingman.client.ui.buttons.HoverButton;
import com.wingman.client.ui.style.OnyxStyleFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FrameTitleBar extends OnyxTitleBar {

    public FrameTitleBar(final JFrame parent) {
        super(parent);

        try {
            // WINGMAN ICON
            JLabel icon = new JLabel();
            icon.setIcon(new ImageIcon(ImageIO.read(Util.getFile("/images/icons/icon_16x16.png"))));

            // TITLE LABEL
            String version = "Developer";
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(Util.getFile("/version.properties")))) {
                version = reader.readLine();
            } catch (NullPointerException e)  {
                //swallow
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel title = new JLabel("Wingman " + version);
            title.setAlignmentX(CENTER_ALIGNMENT);

            // MINIMIZE BUTTON
            HoverButton minimize = new HoverButton();
            minimize.setIcon(new ImageIcon(ImageIO.read(Util.getFile("/images/icons/minimize.png"))));
            minimize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Client.frame.setState(JFrame.ICONIFIED);
                }
            });

            // MAXIMIZE BUTTON
            final ImageIcon maximizeIcon = new ImageIcon(ImageIO.read(Util.getFile("/images/icons/maximize.png")));
            final ImageIcon maximizeIcon2 = new ImageIcon(ImageIO.read(Util.getFile("/images/icons/unmaximize.png")));

            final HoverButton maximize = new HoverButton();
            maximize.setIcon(maximizeIcon);
            maximize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (parent.isResizable()) {
                        if ((parent.getExtendedState() & JFrame.MAXIMIZED_BOTH) == 0) {
                            parent.getRootPane().setBorder(BorderFactory.createEmptyBorder());
                            parent.setExtendedState(parent.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                            maximize.setIcon(maximizeIcon2);
                        } else {
                            parent.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, OnyxStyleFactory.VERY_DARK_BLACK));
                            parent.setExtendedState(JFrame.NORMAL);
                            maximize.setIcon(maximizeIcon);
                        }
                    }
                }
            });

            // EXIT BUTTON
            HoverButton exit = new HoverButton();
            exit.setIcon(new ImageIcon(ImageIO.read(Util.getFile("/images/icons/exit.png"))));
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (Client.clientTrayIcon != null) {
                        Client.clientTrayIcon.detach();
                    }
                    Client.settings.save();
                    PluginManager.deactivatePlugins();

                    System.exit(0);
                }
            });

            this.add(Box.createHorizontalStrut(3));
            this.add(icon);
            this.add(Box.createHorizontalStrut(73));
            this.add(Box.createHorizontalGlue());
            this.add(title);
            this.add(Box.createHorizontalGlue());
            this.add(minimize);
            this.add(Box.createHorizontalStrut(10));
            this.add(maximize);
            this.add(Box.createHorizontalStrut(10));
            this.add(exit);

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        maximize.doClick();
                    }
                }
            });
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
