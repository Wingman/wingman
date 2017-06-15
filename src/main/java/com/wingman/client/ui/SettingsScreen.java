package com.wingman.client.ui;

import com.wingman.client.api.ui.settingscreen.SettingsItem;
import com.wingman.client.api.ui.settingscreen.SettingsSection;
import com.wingman.client.ui.style.OnyxStyleFactory;
import com.wingman.client.ui.titlebars.SettingsTitleBar;
import com.wingman.client.ui.util.ComponentBorderResizer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class SettingsScreen extends JDialog {

    private final List<SettingsSection> sections
            = Collections.synchronizedList(new ArrayList<SettingsSection>());

    private JPanel sectionList = new JPanel();
    private JScrollPane sectionListScrollPane = new JScrollPane(sectionList);
    private JPanel nothingFoundPanel;

    private JTextField searchField = new JTextField();

    private JPanel selectedSectionPanel = new JPanel();
    private SettingsSection selectedSection;

    public SettingsScreen() {
        new ComponentBorderResizer(this);

        this.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 3, 3, 3, OnyxStyleFactory.BASE));
        this.setUndecorated(true);
        this.setJMenuBar(new SettingsTitleBar(this));

        sectionList.setLayout(new BoxLayout(sectionList, BoxLayout.Y_AXIS));
        selectedSectionPanel.setLayout(new BoxLayout(selectedSectionPanel, BoxLayout.Y_AXIS));

        sectionListScrollPane.getVerticalScrollBar().setUnitIncrement(15);

        JPanel contentPane = new JPanel(new BorderLayout() {
            @Override
            public Dimension minimumLayoutSize(Container target) {
                return new Dimension(550, 300);
            }

            @Override
            public Dimension preferredLayoutSize(Container target) {
                return new Dimension(550, 400);
            }
        });

        contentPane.add(sectionListScrollPane);
        contentPane.add(searchField, BorderLayout.SOUTH);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                redrawSectionList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                redrawSectionList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                redrawSectionList();
            }
        });

        this.setContentPane(contentPane);
        this.pack();
        this.setLocationRelativeTo(null);

        searchField.requestFocusInWindow();
    }

    public void registerSection(SettingsSection settingsSection) {
        if (settingsSection != null) {
            if (!sections.contains(settingsSection)) {
                sections.add(settingsSection);
                redrawSectionList();
            }
        }
    }

    public void unregisterSection(SettingsSection settingsSection) {
        if (sections.contains(settingsSection)) {
            if (settingsSection.equals(selectedSection)) {
                deselectSection();
            }

            sections.remove(settingsSection);
            redrawSectionList();
        }
    }

    private void selectSection(SettingsSection section) {
        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.removeAll();

        selectedSectionPanel.removeAll();

        JPanel builtHeader = section.getSelectedHeader();
        if (builtHeader == null) {
            builtHeader = section.buildSelectedSectionHeader();

            builtHeader.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedSection != null) {
                        deselectSection();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }

        selectedSectionPanel.add(builtHeader);
        selectedSectionPanel.add(section.buildSelectedSectionBody());

        contentPane.add(selectedSectionPanel);

        this.revalidate();
        this.repaint();

        selectedSection = section;
    }

    private void deselectSection() {
        selectedSection = null;

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.removeAll();

        contentPane.add(sectionListScrollPane);
        contentPane.add(searchField, BorderLayout.SOUTH);

        redrawSectionList();

        searchField.requestFocusInWindow();
    }

    private synchronized void redrawSectionList() {
        sectionList.removeAll();

        final String searchString = searchField.getText().toLowerCase();

        List<SettingsSection> sectionsToDraw;
        Map<SettingsSection, List<SettingsItem>> settingsToDraw = new HashMap<>();

        synchronized (sections) {
            if (searchString.length() == 0) {
                sectionsToDraw = sections;
            } else {
                sectionsToDraw = new ArrayList<>();

                for (SettingsSection section : sections) {
                    for (SettingsItem item : section.items) {
                        if (item.getDescription().toLowerCase().contains(searchString)) {
                            settingsToDraw
                                    .computeIfAbsent(section, k -> new ArrayList<>())
                                    .add(item);
                        }
                    }

                    if (settingsToDraw.containsKey(section)
                            || section.getOwner().toLowerCase().contains(searchString)
                            || section.getDescription().toLowerCase().contains(searchString)
                            || (section.getPluginId() != null
                            && section.getPluginId().toLowerCase().contains(searchString))) {

                        sectionsToDraw.add(section);
                    }
                }
            }
        }

        if (!sectionsToDraw.isEmpty()) {
            int i = 0;
            for (final SettingsSection section : sectionsToDraw) {
                JPanel builtHeader = section.getListHeader();

                if (builtHeader == null) {
                    builtHeader = section.buildSectionListHeader();

                    builtHeader.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (selectedSection == null) {
                                selectSection(section);
                            }
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                        }
                    });
                }

                List<SettingsItem> settings = settingsToDraw.get(section);
                if (settings != null) {
                    builtHeader.setBackground(OnyxStyleFactory.BASE_DARKER);
                } else {
                    if (i++ % 2 == 0) {
                        builtHeader.setBackground(OnyxStyleFactory.BASE_DARKER);
                    } else {
                        builtHeader.setBackground(OnyxStyleFactory.BASE);
                    }
                }

                sectionList.add(builtHeader);

                if (settings != null) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.setBackground(OnyxStyleFactory.BASE_DARKER);

                    if (sectionsToDraw.size() > 1) {
                        panel.setBorder(new EmptyBorder(0, 30, 0, 0));
                    }

                    int j = 0;
                    for (SettingsItem item : settings) {
                        JPanel itemPanel = item.build();

                        if (j++ % 2 == 1) {
                            itemPanel.setBackground(OnyxStyleFactory.BASE_DARKER);
                        }

                        panel.add(itemPanel);
                    }

                    sectionList.add(panel);
                } else {
                    if (sectionsToDraw.size() == 1) {
                        sectionList.add(section.buildSelectedSectionBody());
                    }
                }
            }
        } else {
            if (nothingFoundPanel == null) {
                nothingFoundPanel = makeNothingFoundPanel();
            }

            sectionList.add(nothingFoundPanel);
        }

        this.revalidate();
        this.repaint();
    }

    private JPanel makeNothingFoundPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel errorLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<b style='font-size:14px'>Couldn't find anything matching your search query</b><br><br>"
                + "The search function looks for results using settings section names,<br>"
                + "their description, plugin IDs and the descriptions of the section's settings");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(errorLabel, BorderLayout.CENTER);

        return panel;
    }
}
