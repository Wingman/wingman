package com.wingman.client.ui;

import com.wingman.client.api.ui.SettingsBar;
import com.wingman.client.ui.style.OnyxListCellRenderer;
import com.wingman.client.ui.style.OnyxStyleFactory;
import com.wingman.client.ui.titlebars.SettingsTitleBar;
import com.wingman.client.ui.util.ComponentBorderResizer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsScreen extends JDialog {

    public Map<String, SettingsBar> settingsBars = new HashMap<>();

    public DefaultListModel<String> buttonListModel = new DefaultListModel<>();
    public final JList<String> buttonList = new JList<>(buttonListModel);

    public JPanel settingsBarPanel = new JPanel();

    public SettingsScreen() {
        new ComponentBorderResizer(this);

        this.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, OnyxStyleFactory.DARK_BLACK));
        this.setUndecorated(true);
        this.setJMenuBar(new SettingsTitleBar(this));
        this.setAlwaysOnTop(true);

        settingsBarPanel.setLayout(new BoxLayout(settingsBarPanel, BoxLayout.Y_AXIS));

        buttonList.setCellRenderer(new OnyxListCellRenderer<String>(true));
        buttonList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                SettingsBar selectedSettingsBar = settingsBars.get(buttonList.getSelectedValue());
                if (selectedSettingsBar != null) {
                    settingsBarPanel.removeAll();
                    settingsBarPanel.add(selectedSettingsBar.panel);
                    Client.settingsScreen.settingsBarPanel.revalidate();
                    Client.settingsScreen.settingsBarPanel.repaint();
                }
            }
        });

        JScrollPane buttonScrollPane = new JScrollPane();
        buttonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        buttonScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        buttonScrollPane.getViewport().add(buttonList);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout() {
            @Override
            public Dimension minimumLayoutSize(Container target) {
                return new Dimension(750, 450);
            }

            @Override
            public Dimension preferredLayoutSize(Container target) {
                return new Dimension(750, 450);
            }
        });

        panel.add(buttonScrollPane, BorderLayout.WEST);
        panel.add(settingsBarPanel, BorderLayout.CENTER);

        this.setContentPane(panel);
    }
}
