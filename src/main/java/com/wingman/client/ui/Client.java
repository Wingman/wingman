package com.wingman.client.ui;

import com.google.common.base.Throwables;
import com.wingman.client.ClientSettings;
import com.wingman.client.Util;
import com.wingman.client.api.settings.PropertiesSettings;
import com.wingman.client.api.ui.SettingsSection;
import com.wingman.client.api.ui.SettingsSectionDesigner;
import com.wingman.client.rs.GameDownloader;
import com.wingman.client.ui.style.OnyxComboBoxUI;
import com.wingman.client.ui.style.OnyxOptionPaneUI;
import com.wingman.client.ui.style.OnyxScrollBarUI;
import com.wingman.client.ui.style.OnyxStyleFactory;
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

    public static PropertiesSettings clientSettings;

    public Client() {
        setupLookAndFeel();
        frame = new JFrame();

        UIManager.put("ScrollBarUI", OnyxScrollBarUI.class.getName());
        UIManager.put("ComboBoxUI", OnyxComboBoxUI.class.getName());
        UIManager.put("OptionPaneUI", OnyxOptionPaneUI.class.getName());
        SwingUtilities.updateComponentTreeUI(frame);

        sideBarBox = new SideBarBox();
        settingsScreen = new SettingsScreen();
        try {
            clientSettings = new ClientSettings();
        } catch (IOException e) {
            Throwables.propagate(e);
        }

        addClientSettings();
        addListeners();

        if (clientSettings.getBoolean(ClientSettings.NOTIFICATIONS_ENABLED)) {
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
        frame.setSize(ClientSettings.APPLET_INITIAL_SIZE);
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
                    if (frame.getWidth() < ClientSettings.APPLET_INITIAL_SIZE.width + sideBarBox.getWidth()) {
                        frame.setSize(new Dimension(ClientSettings.APPLET_INITIAL_SIZE.width + sideBarBox.getWidth() + 8, frame.getHeight()));
                        frame.revalidate();
                    }
                }
            }
        });

        new ComponentBorderResizer(frame);
    }

    /**
     * Registers a {@link SettingsSection} with client related settings.
     */
    private void addClientSettings() {
        SettingsSection settingsSection = new SettingsSection();
        settingsSection.sideText = "Wingman";

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
            settingsPreferredWorld = Integer.parseInt(clientSettings.get(ClientSettings.PREFERRED_WORLD));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            clientSettings.update(ClientSettings.PREFERRED_WORLD, settingsPreferredWorld);
        }
        preferredWorld.setSelectedItem(settingsPreferredWorld);
        preferredWorld.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                clientSettings.update(ClientSettings.PREFERRED_WORLD, "" + (int) e.getItem());
            }
        });

        JPanel worldSettings = SettingsSectionDesigner
                .createSettingsRow("Preferred world", preferredWorld);

        settingsList.add(worldSettings);

        // ENABLE NOTIFICATIONS API
        JCheckBox notificationsEnabled = new JCheckBox();
        notificationsEnabled.setSelected(clientSettings.getBoolean(ClientSettings.NOTIFICATIONS_ENABLED));
        notificationsEnabled.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String newState = e.getStateChange() == ItemEvent.SELECTED ? "true" : "false";
                clientSettings.update(ClientSettings.NOTIFICATIONS_ENABLED, newState);
            }
        });

        JPanel notificationsApi = SettingsSectionDesigner
                .createSettingsRow("Enable notifications API", notificationsEnabled);

        settingsList.add(notificationsApi);

        SettingsSectionDesigner.design(settingsSection, settingsList, true);
        settingsSection.panel.add(Box.createVerticalGlue());
        settingsSection.register();
    }
}
