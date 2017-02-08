package com.wingman.client.ui.titlebars;

import com.wingman.client.plugin.PluginManager;
import com.wingman.client.ui.Client;
import com.wingman.client.ui.components.HoverButton;
import com.wingman.client.ui.style.OnyxStyleFactory;
import com.wingman.client.util.FileUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
            this.add(new JLabel(new ImageIcon(ImageIO.read(FileUtil.getFile("/images/icons/icon_16x16.png")))));
            this.add(Box.createHorizontalStrut(7));

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
            throw new RuntimeException(e);
        }
    }

    private HoverButton makeExpandButton() throws IOException {
        HoverButton hoverButton
                = new HoverButton(new ImageIcon(ImageIO.read(FileUtil.getFile("/images/icons/expand.png"))));

        hoverButton.setMargin(new Insets(3, 0, 3, 0));
        hoverButton.addActionListener(e -> {
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
        });

        return hoverButton;
    }

    private HoverButton makeSettingsButton() throws IOException {
        HoverButton hoverButton
                = new HoverButton(new ImageIcon(ImageIO.read(FileUtil.getFile("/images/icons/settings.png"))));

        hoverButton.setMargin(new Insets(3, 3, 3, 3));
        hoverButton.addActionListener(e -> {
            if (Client.settingsScreen.isVisible()) {
                Client.settingsScreen.setVisible(false);
            } else {
                Client.settingsScreen.setVisible(true);
            }
        });

        return hoverButton;
    }

    private JLabel makeTitleText() {
        String version = "Developer";

        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(FileUtil.getFile("/version.properties")))) {
            version = reader.readLine();
        } catch (NullPointerException ignored)  {
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel titleLabel = new JLabel("Wingman " + version);

        titleLabel.setForeground(OnyxStyleFactory.SECONDARY_TEXT_COLOR);
        titleLabel.setFont(OnyxStyleFactory.ROBOTO_MEDIUM);

        return titleLabel;
    }

    private HoverButton makeMinimizeButton() throws IOException {
        HoverButton hoverButton
                = new HoverButton(new ImageIcon(ImageIO.read(FileUtil.getFile("/images/icons/minimize.png"))));
        hoverButton.addActionListener(e -> Client.frame.setState(JFrame.ICONIFIED));
        return hoverButton;
    }

    private HoverButton makeMaximizeButton() throws IOException {
        ImageIcon maximizeIcon = new ImageIcon(ImageIO.read(FileUtil.getFile("/images/icons/maximize.png")));
        ImageIcon maximizeIcon2 = new ImageIcon(ImageIO.read(FileUtil.getFile("/images/icons/unmaximize.png")));

        HoverButton hoverButton = new HoverButton(maximizeIcon);
        hoverButton.addActionListener(e -> {
            if (parent.isResizable()) {
                if ((parent.getExtendedState() & JFrame.MAXIMIZED_BOTH) == 0) {
                    parent.getRootPane().setBorder(BorderFactory.createEmptyBorder());
                    parent.setExtendedState(parent.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                    hoverButton.setIcon(maximizeIcon2);
                } else {
                    parent.getRootPane().setBorder(BorderFactory.createMatteBorder(
                            0, 4, 4, 4,
                            OnyxStyleFactory.BASE));
                    parent.setExtendedState(JFrame.NORMAL);
                    hoverButton.setIcon(maximizeIcon);
                }
            }
        });
        return hoverButton;
    }

    private HoverButton makeExitButton() throws IOException {
        HoverButton hoverButton
                = new HoverButton(new ImageIcon(ImageIO.read(FileUtil.getFile("/images/icons/exit.png"))));
        hoverButton.addActionListener(e -> {
            if (Client.clientTrayIcon != null) {
                Client.clientTrayIcon.detach();
            }

            PluginManager.deactivatePlugins();

            System.exit(0);
        });
        return hoverButton;
    }
}
