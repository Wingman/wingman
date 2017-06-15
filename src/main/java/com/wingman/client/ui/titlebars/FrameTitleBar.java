package com.wingman.client.ui.titlebars;

import com.wingman.client.plugin.PluginManager;
import com.wingman.client.ui.AppletFX;
import com.wingman.client.ui.Client;
import com.wingman.client.ui.style.OnyxStyleFactory;
import com.wingman.client.util.FileUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FrameTitleBar extends TitleBar {

    private ToggleButton maximizeButton;

    public FrameTitleBar(JFrame parent) {
        super(parent);

        try {
            String stylesheetPath = AppletFX
                    .class
                    .getResource("/skins/onyx/frameTitleBar.css")
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
                        .addAll(createSettingsButton(),
                                createExpandButton(),
                                createMinimizeButton(),
                                maximizeButton = createMaximizeButton(),
                                createExitButton());

                BorderPane borderPane = new BorderPane();

                borderPane.setLeft(leftSide);
                borderPane.setRight(rightSide);

                contentPanelPane.setCenter(borderPane);
            });

            contentPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        maximizeButton.fire();
                    }
                }
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

        String version = "Developer";

        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(FileUtil.getFile("/version.properties")))) {
            version = reader.readLine();
        } catch (NullPointerException ignored)  {
        } catch (IOException e) {
            e.printStackTrace();
        }

        Label label = new Label("Wingman " + version);

        pane.getChildren().add(label);

        return pane;
    }

    private Button createSettingsButton() {
        Button button = new Button();

        button.setId("settingsButton");
        button.setOnAction((e) -> {
            if (Client.settingsScreen.isVisible()) {
                Client.settingsScreen.setVisible(false);
            } else {
                Client.settingsScreen.setVisible(true);
            }
        });

        return button;
    }

    private Button createExpandButton() {
        Button button = new Button();

        button.setId("expandButton");
        // TODO: Add click listener when side bars are implemented

        return button;
    }

    private Button createMinimizeButton() {
        Button button = new Button();

        button.setId("minimizeButton");
        button.setOnAction((e) -> Client.frame.setState(JFrame.ICONIFIED));

        return button;
    }

    private ToggleButton createMaximizeButton() {
        ToggleButton button = new ToggleButton();

        button.setId("maximizeButton");
        button.setOnAction((e) -> {
            JFrame frame = (JFrame) parent;

            if (frame.isResizable()) {
                if ((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == 0) {
                    frame.getRootPane().setBorder(BorderFactory.createEmptyBorder());
                    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                } else {
                    frame.getRootPane().setBorder(BorderFactory.createMatteBorder(
                            0, 3, 3, 3,
                            OnyxStyleFactory.BASE));
                    frame.setExtendedState(JFrame.NORMAL);
                }
            }
        });

        return button;
    }

    private Button createExitButton() {
        Button button = new Button();

        button.setId("exitButton");
        button.setOnAction((e) -> {
            if (Client.clientTrayIcon != null) {
                Client.clientTrayIcon.detach();
            }

            PluginManager.deactivatePlugins();

            System.exit(0);
        });

        return button;
    }
}
