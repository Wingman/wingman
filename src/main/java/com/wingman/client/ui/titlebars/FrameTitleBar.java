package com.wingman.client.ui.titlebars;

import com.google.common.base.Throwables;
import com.wingman.client.Util;
import com.wingman.client.plugin.PluginManager;
import com.wingman.client.ui.Client;
import com.wingman.client.ui.buttons.HoverButton;
import com.wingman.client.ui.style.OnyxStyleFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FrameTitleBar extends OnyxTitleBar {

    public JFrame parent;

    public final HoverButton minimizeButton;
    public final HoverButton maximizeButton;
    public final HoverButton exitButton;

    public FrameTitleBar(final JFrame parent) {
        super(parent);
        this.parent = parent;

        try {
            this.add(Box.createHorizontalStrut(5));
            this.add(new JLabel(new ImageIcon(ImageIO.read(Util.getFile("/images/icons/icon_16x16.png")))));
            this.add(Box.createHorizontalStrut(7));

            //this.add(Box.createHorizontalGlue());
            this.add(makeTitleText());
            this.add(Box.createHorizontalGlue());

            this.add(makeSettingsButton());
            this.add(Box.createHorizontalStrut(10));
            this.add(makeExpandButton());
            this.add(Box.createHorizontalStrut(20));

            this.add(minimizeButton = makeMinimizeButton());
            this.add(Box.createHorizontalStrut(10));
            this.add(maximizeButton = makeMaximizeButton());
            this.add(Box.createHorizontalStrut(10));
            this.add(exitButton = makeExitButton());
            this.add(Box.createHorizontalStrut(2));

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        maximizeButton.doClick();
                    }
                }
            });
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    private HoverButton makeExpandButton() throws IOException {
        HoverButton hoverButton = new HoverButton();
        hoverButton.setIcon(new ImageIcon(ImageIO.read(Util.getFile("/images/icons/expand.png"))));
        hoverButton.setMargin(new Insets(3, 0, 3, 0));
        hoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean maximized = (Client.frame.getExtendedState() & Frame.MAXIMIZED_HORIZ) != 0;
                Client.sideBarBox.setVisible(!Client.sideBarBox.isVisible());
                Client.frame.revalidate();
                Client.frame.repaint();
                int newWidth = Client.frame.getWidth();
                if (Client.sideBarBox.isVisible()) {
                    if (!maximized) {
                        newWidth += Client.sideBarBox.getWidth();
                    }
                } else {
                    if (!maximized) {
                        newWidth -= Client.sideBarBox.getWidth();
                    }
                }
                Client.frame.setSize(new Dimension(newWidth, Client.frame.getHeight()));
                Client.frame.revalidate();
                Client.frame.repaint();
            }
        });
        return hoverButton;
    }

    private HoverButton makeSettingsButton() throws IOException {
        HoverButton hoverButton = new HoverButton();
        hoverButton.setIcon(new ImageIcon(ImageIO.read(Util.getFile("/images/icons/settings.png"))));
        hoverButton.setMargin(new Insets(3, 3, 3, 3));
        hoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Client.settingsScreen.isVisible()) {
                    Client.settingsScreen.setVisible(false);
                } else {
                    Client.settingsScreen.setVisible(true);
                }
            }
        });
        return hoverButton;
    }

    private JLabel makeTitleText() {
        String version = "Developer";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Util.getFile("/version.properties")))) {
            version = reader.readLine();
        } catch (NullPointerException e)  {
            //swallow
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel titleLabel = new JLabel("Wingman " + version);
        titleLabel.setFont(OnyxStyleFactory.ROBOTO_MEDIUM);
        return titleLabel;
    }

    private HoverButton makeMinimizeButton() throws IOException {
        HoverButton hoverButton = new HoverButton();
        hoverButton.setIcon(new ImageIcon(ImageIO.read(Util.getFile("/images/icons/minimize.png"))));
        hoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.frame.setState(JFrame.ICONIFIED);
            }
        });
        return hoverButton;
    }

    private HoverButton makeMaximizeButton() throws IOException {
        final ImageIcon maximizeIcon = new ImageIcon(ImageIO.read(Util.getFile("/images/icons/maximize.png")));
        final ImageIcon maximizeIcon2 = new ImageIcon(ImageIO.read(Util.getFile("/images/icons/unmaximize.png")));

        final HoverButton hoverButton = new HoverButton();
        hoverButton.setIcon(maximizeIcon);
        hoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parent.isResizable()) {
                    if ((parent.getExtendedState() & JFrame.MAXIMIZED_BOTH) == 0) {
                        parent.getRootPane().setBorder(BorderFactory.createEmptyBorder());
                        parent.setExtendedState(parent.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                        hoverButton.setIcon(maximizeIcon2);
                    } else {
                        parent.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, OnyxStyleFactory.DARK_BLACK));
                        parent.setExtendedState(JFrame.NORMAL);
                        hoverButton.setIcon(maximizeIcon);
                    }
                }
            }
        });
        return hoverButton;
    }

    private HoverButton makeExitButton() throws IOException {
        HoverButton hoverButton = new HoverButton();
        hoverButton.setIcon(new ImageIcon(ImageIO.read(Util.getFile("/images/icons/exit.png"))));
        hoverButton.addActionListener(new ActionListener() {
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
        return hoverButton;
    }
}
