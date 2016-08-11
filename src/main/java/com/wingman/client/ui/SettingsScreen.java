package com.wingman.client.ui;

import com.wingman.client.api.ui.settingscreen.SettingsSection;
import com.wingman.client.ui.style.OnyxStyleFactory;
import com.wingman.client.ui.titlebars.SettingsTitleBar;
import com.wingman.client.ui.util.ComponentBorderResizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsScreen extends JDialog {

    private final List<SettingsSection> sections
            = Collections.synchronizedList(new ArrayList<SettingsSection>());

    private JPanel sectionList = new JPanel();
    private JScrollPane sectionListScrollPane = new JScrollPane(sectionList);

    private JPanel selectedSectionPanel = new JPanel();
    private SettingsSection selectedSection;

    public SettingsScreen() {
        new ComponentBorderResizer(this);

        this.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, OnyxStyleFactory.BASE));
        this.setUndecorated(true);
        this.setJMenuBar(new SettingsTitleBar(this));

        sectionList.setLayout(new BoxLayout(sectionList, BoxLayout.Y_AXIS));
        selectedSectionPanel.setLayout(new BoxLayout(selectedSectionPanel, BoxLayout.Y_AXIS));

        JPanel contentPane = new JPanel(new BorderLayout() {
            @Override
            public Dimension minimumLayoutSize(Container target) {
                return new Dimension(750, 450);
            }

            @Override
            public Dimension preferredLayoutSize(Container target) {
                return new Dimension(750, 450);
            }
        });

        contentPane.add(sectionListScrollPane);

        this.setContentPane(contentPane);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private synchronized void redrawSectionList() {
        sectionList.removeAll();

        synchronized (sections) {
            int i = 0;
            for (final SettingsSection section : sections) {
                JPanel builtHeader = section.getListHeader();

                if (builtHeader == null) {
                    builtHeader = section.buildSectionListHeader();

                    builtHeader.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (selectedSection == null) {
                                showSectionBody(section);
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

                if (i++ % 2 == 0) {
                    builtHeader.setBackground(OnyxStyleFactory.BASE_DARKER);
                }

                sectionList.add(builtHeader);
            }
        }

        this.revalidate();
        this.repaint();
    }

    private void showSectionBody(SettingsSection section) {
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
                        hideSectionBody();
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

        JScrollPane builtBody = section.getSelectedBody();
        if (builtBody == null) {
            builtBody = section.buildSelectedSectionBody();
        }

        selectedSectionPanel.add(builtHeader);
        selectedSectionPanel.add(builtBody);

        contentPane.add(selectedSectionPanel);
        contentPane.revalidate();
        contentPane.repaint();

        selectedSection = section;
    }

    private void hideSectionBody() {
        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.removeAll();

        contentPane.add(sectionListScrollPane);
        contentPane.revalidate();
        contentPane.repaint();

        selectedSection = null;
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
                hideSectionBody();
            }

            sections.remove(settingsSection);
            redrawSectionList();
        }
    }
}
