package com.wingman.client.ui;

import com.google.common.base.Throwables;
import com.wingman.client.Settings;
import com.wingman.client.Util;
import com.wingman.client.api.ui.SettingsBar;
import com.wingman.client.api.ui.SettingsBarDesigner;
import com.wingman.client.rs.GameDownloader;
import com.wingman.client.ui.style.OnyxComboBoxUI;
import com.wingman.client.ui.style.OnyxScrollBarUI;
import com.wingman.client.ui.style.OnyxStyleFactory;
import com.wingman.client.ui.style.OnyxTabbedPaneUI;
import com.wingman.client.ui.titlebars.FrameTitleBar;
import com.wingman.client.ui.util.ComponentBorderResizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
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

    public static JFrame frame;
    public static JPanel framePanel;
    public static SideBarBox sideBarBox;

    public static SettingsScreen settingsScreen;

    public static ClientTrayIcon clientTrayIcon;

    public static Settings settings = new Settings();

    public Client() {
        setupLookAndFeel();
        frame = new JFrame();

        UIManager.put("ScrollBarUI", OnyxScrollBarUI.class.getName());
        UIManager.put("ComboBoxUI", OnyxComboBoxUI.class.getName());
        SwingUtilities.updateComponentTreeUI(frame);

        sideBarBox = new SideBarBox();
        settingsScreen = new SettingsScreen();

        addClientSettings();
        addListeners();

        if (settings.getBoolean(Settings.NOTIFICATIONS_ENABLED)) {
            try {
                clientTrayIcon = new ClientTrayIcon();
            } catch (IOException | AWTException e) {
                e.printStackTrace();
            }
        }

        framePanel = new JPanel(new BorderLayout());
        framePanel.setForeground(OnyxStyleFactory.LIGHT_WHITE);
        framePanel.setBackground(OnyxStyleFactory.DARK_BLACK);

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

        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, OnyxStyleFactory.DARK_BLACK));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setTitle("Wingman");
        frame.setUndecorated(true);
        frame.setJMenuBar(new FrameTitleBar(frame));
        frame.setContentPane(framePanel);
        frame.setSize(Settings.APPLET_INITIAL_SIZE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.toFront();

        new GameDownloader();
    }

    /**
     * Sets up the Look and Feel of the client.
     */
    private static void setupLookAndFeel() {
        try {
            SynthLookAndFeel synthLookAndFeel = new SynthLookAndFeel();
            UIManager.setLookAndFeel(synthLookAndFeel);
            SynthLookAndFeel.setStyleFactory(new OnyxStyleFactory());
        } catch (UnsupportedLookAndFeelException  e) {
            Throwables.propagate(e);
        }

        // Prevent the applet from overlapping the menus
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
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

        // PREFERRED WORLD
        JComboBox<Integer> preferredWorld = new JComboBox<Integer>() {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(80, 20);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(80, 20);
            }
        };
        for (int i = 301; i <= 399; i++) {
            preferredWorld.addItem(i);
        }
        int settingsPreferredWorld = 311;
        try {
            settingsPreferredWorld = Integer.parseInt(settings.get(Settings.PREFERRED_WORLD));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            settings.update(Settings.PREFERRED_WORLD, settingsPreferredWorld);
        }
        preferredWorld.setSelectedItem(settingsPreferredWorld);
        preferredWorld.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                settings.update(Settings.PREFERRED_WORLD, "" + (int) e.getItem());
            }
        });

        JPanel worldSettings = SettingsBarDesigner
                .createSettingsRow("Preferred world", preferredWorld);

        settingsList.add(worldSettings);

        // ENABLE NOTIFICATIONS API
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
