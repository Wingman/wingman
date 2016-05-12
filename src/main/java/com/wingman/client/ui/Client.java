package com.wingman.client.ui;

import com.wingman.client.Settings;
import com.wingman.client.Util;
import com.wingman.client.api.ui.SettingsBar;
import com.wingman.client.api.ui.SettingsBarDesigner;
import com.wingman.client.rs.GameDownloader;
import com.wingman.client.ui.style.OnyxStyleFactory;
import com.wingman.client.ui.titlebars.FrameTitleBar;
import com.wingman.client.ui.toolbars.FrameToolbar;
import com.wingman.client.ui.util.ComponentBorderResizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 * {@link Client} is the core of the client and is the first class to be invoked after the main method.
 */
public class Client {

    public static JFrame frame = new JFrame();

    public static JPanel framePanel = new JPanel();
    public static FrameToolbar frameToolbar = new FrameToolbar();
    public static SideBarBox sideBarBox = new SideBarBox();

    public static SettingsScreen settingsScreen = new SettingsScreen();

    public static Settings settings = new Settings();
    public static ClientTrayIcon clientTrayIcon;

    public Client() {
        addClientSettings();
        addListeners();

        if (settings.getBoolean(Settings.NOTIFICATIONS_ENABLED)) {
            try {
                clientTrayIcon = new ClientTrayIcon();
            } catch (IOException | AWTException e) {
                e.printStackTrace();
            }
        }

        framePanel.setLayout(new BorderLayout());
        framePanel.setForeground(OnyxStyleFactory.VERY_LIGHT_WHITE);
        framePanel.setBackground(OnyxStyleFactory.VERY_DARK_BLACK);

        try {
            ArrayList<Image> icons = new ArrayList<>();
            icons.add(ImageIO.read(Util.getFile("/images/icons/icon_16x16.png")));
            icons.add(ImageIO.read(Util.getFile("/images/icons/icon_32x32.png")));
            icons.add(ImageIO.read(Util.getFile("/images/icons/icon_64x64.png")));
            icons.add(ImageIO.read(Util.getFile("/images/icons/icon_128x128.png")));
            frame.setIconImages(icons);

            JLabel loadingImage = new JLabel(new ImageIcon(Util.getFileAsBytes("/images/loading.png")));
            framePanel.add(loadingImage, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, OnyxStyleFactory.VERY_DARK_BLACK));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setTitle("Wingman");
        frame.setUndecorated(true);
        frame.setJMenuBar(new FrameTitleBar(frame));
        frame.setContentPane(framePanel);
        frame.setSize(Settings.APPLET_INITIAL_SIZE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.toFront();

        new GameDownloader();
    }

    /**
     * Adds the default client frame/program listeners.
     */
    private void addListeners() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (sideBarBox.isVisible()) {
                    if (frame.getWidth() < Settings.APPLET_INITIAL_SIZE.width + sideBarBox.getWidth()) {
                        frame.setSize(new Dimension(Settings.APPLET_INITIAL_SIZE.width + sideBarBox.getWidth() + 8, frame.getHeight()));
                        frame.revalidate();
                    }
                }
            }
        });

        new ComponentBorderResizer(frame);
    }

    /**
     * Registers a {@link SettingsBar} with client related settings.
     */
    private void addClientSettings() {
        SettingsBar settingsBar = new SettingsBar();
        settingsBar.sideText = "Wingman";

        ArrayList<JPanel> settingsList = new ArrayList<>();

        final JTextField preferredWorld = new JTextField(settings.get(Settings.PREFERRED_WORLD));
        preferredWorld.setPreferredSize(new Dimension(30, 25));
        preferredWorld.setMaximumSize(new Dimension(20, 25));
        preferredWorld.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                settings.update(Settings.PREFERRED_WORLD, preferredWorld.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                settings.update(Settings.PREFERRED_WORLD, preferredWorld.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        JPanel worldSettings = SettingsBarDesigner
                .createSettingsRow("Preferred world", preferredWorld);

        settingsList.add(worldSettings);

        JCheckBox notificationsEnabled = new JCheckBox();
        notificationsEnabled.setMargin(new Insets(0, 10, 0, 0));
        notificationsEnabled.setSelected(settings.getBoolean(Settings.NOTIFICATIONS_ENABLED));
        notificationsEnabled.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String newState = e.getStateChange() == ItemEvent.SELECTED ? "true" : "false";
                settings.update(Settings.NOTIFICATIONS_ENABLED, newState);
            }
        });
        JPanel notificationsApi = SettingsBarDesigner
                .createSettingsRow("Enable notifications API", notificationsEnabled);

        settingsList.add(notificationsApi);

        SettingsBarDesigner.designSettingsBar(settingsBar, settingsList, true);
        settingsBar.panel.add(Box.createVerticalGlue());
        settingsBar.register();
    }
}
