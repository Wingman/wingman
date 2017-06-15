package com.wingman.client.ui.titlebars;

import com.wingman.client.ui.util.AppletFX;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.swing.*;

public class SettingsTitleBar extends TitleBar {

    public SettingsTitleBar(JDialog parent) {
        super(parent);

        try {
            String stylesheetPath = AppletFX
                    .class
                    .getResource("/skins/onyx/settingsTitleBar.css")
                    .toExternalForm();

            contentPanel
                    .getScene()
                    .getStylesheets()
                    .add(stylesheetPath);

            AppletFX.runAndWait(contentPanel, () -> {
                BorderPane contentPanelPane = (BorderPane) contentPanel
                        .getScene()
                        .getRoot();

                HBox leftSide = new HBox();

                leftSide.setId("leftSide");
                leftSide.getChildren()
                        .addAll(createClientIcon(), createTitleText());

                HBox rightSide = new HBox();

                rightSide.setId("rightSide");
                rightSide.getChildren()
                        .addAll(createExitButton());

                BorderPane borderPane = new BorderPane();

                borderPane.setLeft(leftSide);
                borderPane.setRight(rightSide);

                contentPanelPane.setCenter(borderPane);
            });

            this.add(contentPanel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Pane createClientIcon() {
        Pane pane = new Pane();
        pane.setId("clientIcon");
        return pane;
    }

    private Pane createTitleText() {
        Pane pane = new Pane();

        pane.setId("titleText");
        pane.getChildren()
                .add(new Label("Wingman/Plugin Settings"));

        return pane;
    }

    private Button createExitButton() {
        Button button = new Button();

        button.setId("exitButton");
        button.setOnAction((e) -> parent.setVisible(false));

        return button;
    }
}
