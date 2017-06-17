package com.wingman.client.api.ui.settingscreen;

import com.wingman.client.api.plugin.Plugin;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsSection {

    private final String owner;
    private final String description;
    private final String pluginVersion;
    private final String pluginId;

    private final SettingSectionToggleListener toggleListener;
    private final boolean defaultToggleState;

    public List<SettingsItem> items = new ArrayList<>();

    private BorderPane builtListHeader;
    private BorderPane builtSelectedHeader;

    /*
        Plugin specific constructors
     */

    public SettingsSection(Plugin plugin) {
        this(plugin, null, false);
    }

    public SettingsSection(Plugin plugin,
                           SettingSectionToggleListener toggleListener,
                           boolean defaultToggleState) {

        this(plugin.name(), plugin.description(), plugin, toggleListener, defaultToggleState);
    }

    public SettingsSection(String owner,
                           String description,
                           Plugin plugin) {

        this(owner, description, plugin, null, false);
    }

    public SettingsSection(String owner,
                           String description,
                           Plugin plugin,
                           SettingSectionToggleListener toggleListener,
                           boolean defaultToggleState) {

        this(owner, description, plugin.version(), plugin.id(), toggleListener, defaultToggleState);
    }

    /*
        General constructors
     */

    public SettingsSection(String owner, String description) {
        this(owner, description, null, false);
    }

    public SettingsSection(String owner,
                           String description,
                           SettingSectionToggleListener toggleListener,
                           boolean defaultToggleState) {

        this(owner, description, null, null, toggleListener, defaultToggleState);
    }

    private SettingsSection(String owner,
                           String description,
                           String pluginVersion,
                           String pluginId,
                           SettingSectionToggleListener toggleListener,
                           boolean defaultToggleState) {

        this.owner = owner;
        this.description = description;
        this.pluginVersion = pluginVersion;
        this.pluginId = pluginId;
        this.toggleListener = toggleListener;
        this.defaultToggleState = defaultToggleState;
    }

    public void add(SettingsItem... items) {
        if (items != null) {
            Collections.addAll(this.items, items);
        }
    }

    public BorderPane buildSectionListHeader() {
        BorderPane panel = new BorderPane();

        panel.getStyleClass()
                .add("header");

        Label ownerLabel = new Label(getOwner());

        ownerLabel
                .getStyleClass()
                .add("owner");

        VBox leftBox = new VBox(
                ownerLabel,
                new Label(getDescription())
        );

        leftBox.getStyleClass()
                .add("leftBox");

        BorderPane.setAlignment(leftBox, Pos.CENTER);

        panel.setLeft(leftBox);

        if (toggleListener != null) {
            CheckBox enableCheckBox = new CheckBox();

            enableCheckBox.setSelected(defaultToggleState);

            enableCheckBox
                    .selectedProperty()
                    .addListener((observable, oldValue, newValue) -> toggleListener.toggled(newValue));

            BorderPane.setAlignment(enableCheckBox, Pos.CENTER);

            panel.setRight(enableCheckBox);
        }

        return builtListHeader = panel;
    }

    public BorderPane buildSelectedSectionHeader() {
        BorderPane panel = new BorderPane();

        panel.getStyleClass()
                .add("header");

        Label ownerLabel;

        if (pluginId != null && pluginVersion != null) {
            ownerLabel = new Label(getOwner() + " (" + getPluginId() + " " + getPluginVersion() + ")");
        } else {
            ownerLabel = new Label(getOwner());
        }

        ownerLabel
                .getStyleClass()
                .add("owner");

        VBox leftBox = new VBox(
                ownerLabel,
                new Label(getDescription())
        );

        leftBox.getStyleClass()
                .add("leftBox");

        BorderPane.setAlignment(leftBox, Pos.CENTER);

        panel.setLeft(leftBox);

        if (toggleListener != null) {
            CheckBox enableCheckBox = new CheckBox();

            enableCheckBox.setSelected(defaultToggleState);

            enableCheckBox
                    .selectedProperty()
                    .addListener((observable, oldValue, newValue) -> toggleListener.toggled(newValue));

            BorderPane.setAlignment(enableCheckBox, Pos.CENTER);

            panel.setRight(enableCheckBox);
        }

        return builtSelectedHeader = panel;
    }

    public ScrollPane buildSelectedSectionBody() {
        VBox itemBox = new VBox();

        for (int i = 0; i < items.size(); i++) {
            SettingsItem item = items.get(i);

            BorderPane itemPane = item.build();

            if (i % 2 != 0) {
                itemPane.getStyleClass()
                        .add("alternatedColor");
            } else {
                itemPane.getStyleClass()
                        .remove("alternatedColor");
            }

            itemBox.getChildren()
                    .add(itemPane);
        }

        ScrollPane scrollPane = new ScrollPane(itemBox);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public String getPluginId() {
        return pluginId;
    }

    public BorderPane getListHeader() {
        return builtListHeader;
    }

    public BorderPane getSelectedHeader() {
        return builtSelectedHeader;
    }
}
