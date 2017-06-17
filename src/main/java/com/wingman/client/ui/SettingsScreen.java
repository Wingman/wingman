package com.wingman.client.ui;

import com.wingman.client.api.ui.settingscreen.SettingsItem;
import com.wingman.client.api.ui.settingscreen.SettingsSection;
import com.wingman.client.ui.style.OnyxStyleFactory;
import com.wingman.client.ui.titlebars.SettingsTitleBar;
import com.wingman.client.ui.util.AppletFX;
import com.wingman.client.ui.util.ComponentBorderResizer;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsScreen extends JDialog {

    private static final Dimension MINIMUM_SIZE = new Dimension(800, 500);

    private JFXPanel contentPanePanel;

    private List<SettingsSection> sections = new ArrayList<>();
    private VBox sectionList = new VBox();
    private AnchorPane sectionListScrollPaneAnchorPane;

    private TextField searchField;

    private SettingsSection selectedSection;
    private boolean initialSectionHasBeenDeselected;

    public SettingsScreen() {
        new ComponentBorderResizer(this);

        this.getRootPane()
                .setBorder(BorderFactory.createMatteBorder(0, 3, 3, 3, OnyxStyleFactory.BASE));
        this.setUndecorated(true);
        this.setJMenuBar(new SettingsTitleBar(this));

        JPanel contentPane = new JPanel(new BorderLayout() {
            @Override
            public Dimension minimumLayoutSize(Container target) {
                return MINIMUM_SIZE;
            }

            @Override
            public Dimension preferredLayoutSize(Container target) {
                return MINIMUM_SIZE;
            }
        });

        contentPanePanel = AppletFX.createPanel();

        String stylesheetPath = AppletFX
                .class
                .getResource("/skins/onyx/settingsScreen.css")
                .toExternalForm();

        contentPanePanel
                .getScene()
                .getStylesheets()
                .add(stylesheetPath);

        AppletFX.runAndWait(contentPanePanel, () -> {
            sectionList.setId("sectionList");

            ScrollPane _sectionListScrollPane = new ScrollPane();
            _sectionListScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            _sectionListScrollPane.setContent(sectionList);
            _sectionListScrollPane.setFitToWidth(true);

            BorderPane rootPane = (BorderPane) contentPanePanel
                    .getScene()
                    .getRoot();

            rootPane.setId("settingsScreen");

            sectionListScrollPaneAnchorPane = new AnchorPane(_sectionListScrollPane);

            AnchorPane.setTopAnchor(_sectionListScrollPane, 0.0);
            AnchorPane.setBottomAnchor(_sectionListScrollPane, 0.0);
            AnchorPane.setLeftAnchor(_sectionListScrollPane, 0.0);
            AnchorPane.setRightAnchor(_sectionListScrollPane, 0.0);

            rootPane.setCenter(sectionListScrollPaneAnchorPane);

            searchField = new TextField();
            searchField.setPromptText("Search for a setting");
            searchField
                    .textProperty()
                    .addListener((observable, oldValue, newValue) -> redrawSectionList());

            rootPane.setBottom(searchField);
        });

        contentPane.add(contentPanePanel, BorderLayout.CENTER);

        this.setContentPane(contentPane);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void registerSection(SettingsSection settingsSection) {
        if (settingsSection != null) {
            AppletFX.runAndWait(contentPanePanel, () -> {
                if (!sections.contains(settingsSection)) {
                    sections.add(settingsSection);

                    if (sections.size() == 1) {
                        selectSection(settingsSection);
                    } else if (!initialSectionHasBeenDeselected) {
                        deselectSection();
                        initialSectionHasBeenDeselected = true;
                    }

                    redrawSectionList();
                }
            });
        }
    }

    private void selectSection(SettingsSection section) {
        BorderPane builtHeader = section.getSelectedHeader();

        if (builtHeader == null) {
            builtHeader = section.buildSelectedSectionHeader();

            builtHeader.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (selectedSection != null) {
                    deselectSection();
                }

                event.consume();
            });
        }

        BorderPane rootPane = (BorderPane) contentPanePanel
                .getScene()
                .getRoot();

        rootPane.setTop(builtHeader);
        rootPane.setCenter(section.buildSelectedSectionBody());
        rootPane.setBottom(null);

        selectedSection = section;
    }

    private void deselectSection() {
        selectedSection = null;

        BorderPane rootPane = (BorderPane) contentPanePanel
                .getScene()
                .getRoot();

        rootPane.setTop(null);
        rootPane.setCenter(sectionListScrollPaneAnchorPane);
        rootPane.setBottom(searchField);

        redrawSectionList();

        searchField.requestFocus();
    }

    private synchronized void redrawSectionList() {
        sectionList
                .getChildren()
                .clear();

        String searchString = searchField.getText();

        List<SettingsSection> sectionsToDraw;
        Map<SettingsSection, List<SettingsItem>> settingsToDraw = new HashMap<>();

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

        if (!sectionsToDraw.isEmpty()) {
            /*
                Draw the headers of either all settings
                sections or items that match the search query.
             */

            for (int i = 0; i < sectionsToDraw.size(); i++) {
                SettingsSection section = sectionsToDraw.get(i);

                BorderPane builtHeader;

                if (searchString.length() != 0) {
                    builtHeader = section.getSelectedHeader();

                    if (builtHeader == null) {
                        builtHeader = section.buildSelectedSectionHeader();

                        builtHeader.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                            if (selectedSection == null) {
                                selectSection(section);
                            }

                            event.consume();
                        });
                    }
                } else {
                    builtHeader = section.getListHeader();

                    if (builtHeader == null) {
                        builtHeader = section.buildSectionListHeader();

                        builtHeader.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                            if (selectedSection == null) {
                                selectSection(section);
                            }

                            event.consume();
                        });
                    }
                }

                builtHeader
                        .getStyleClass()
                        .remove("alternatedColor");

                if (settingsToDraw.isEmpty()) {
                    if (i % 2 != 0) {
                        builtHeader
                                .getStyleClass()
                                .add("alternatedColor");
                    }
                }

                sectionList
                        .getChildren()
                        .add(builtHeader);

                List<SettingsItem> settings = settingsToDraw.get(section);

                if (settings != null) {
                    VBox itemBox = new VBox();

                    for (int j = 0; j < settings.size(); j++) {
                        SettingsItem item = settings.get(j);

                        BorderPane itemPane = item.build();

                        if (j % 2 != 0) {
                            itemPane.getStyleClass()
                                    .add("alternatedColor");
                        } else {
                            itemPane.getStyleClass()
                                    .remove("alternatedColor");
                        }

                        itemBox.getChildren()
                                .add(itemPane);
                    }

                    sectionList
                            .getChildren()
                            .add(itemBox);
                }
            }
        }

        this.revalidate();
        this.repaint();
    }
}
