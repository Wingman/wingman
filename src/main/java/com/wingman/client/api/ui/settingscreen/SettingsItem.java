package com.wingman.client.api.ui.settingscreen;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsItem {

    private final String description;

    public List<Node> components = new ArrayList<>();

    public SettingsItem(String description) {
        this.description = description;
    }

    public void add(Node... components) {
        if (components != null) {
            Collections.addAll(this.components, components);
        }
    }

    public BorderPane build() {
        BorderPane panel = new BorderPane();

        panel.getStyleClass()
                .add("item");

        VBox rightPanel = new VBox();

        rightPanel.setAlignment(Pos.CENTER_RIGHT);

        for (Node component : components) {
            rightPanel
                    .getChildren()
                    .add(component);
        }

        Label descriptionLabel = new Label(getDescription());

        BorderPane.setAlignment(descriptionLabel, Pos.CENTER);
        BorderPane.setAlignment(rightPanel, Pos.CENTER);

        panel.setLeft(descriptionLabel);
        panel.setRight(rightPanel);

        return panel;
    }

    public String getDescription() {
        return description;
    }
}
