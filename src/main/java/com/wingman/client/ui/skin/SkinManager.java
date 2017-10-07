package com.wingman.client.ui.skin;

import com.wingman.client.api.ui.skin.Skin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SkinManager {

    private static final Object SYNC_OBJECT = new Object();
    private static Skin currentSkin;
    private static List<JFXPanel> panels = new ArrayList<>();

    /**
     * Sets the global skin that is
     * applied to new and existing panels.
     *
     * @param skin the skin to apply
     */
    public static void applySkin(Skin skin) {
        if (skin == null) {
            throw new IllegalStateException("skin cannot be null");
        }

        synchronized (SYNC_OBJECT) {
            if (skin != currentSkin) {
                Skin previousSkin = currentSkin;
                currentSkin = skin;

                Application.setUserAgentStylesheet(skin.getBaseStylesheetPath());

                if (previousSkin != null) {
                    String[] previousStyleSheets = {
                            previousSkin.getSettingsScreenStylesheetPath(),
                            previousSkin.getFrameTitleBarStylesheetPath(),
                            previousSkin.getSettingsTitleBarStylesheetPath()
                    };

                    String[] newStyleSheets = {
                            skin.getSettingsScreenStylesheetPath(),
                            skin.getFrameTitleBarStylesheetPath(),
                            skin.getSettingsTitleBarStylesheetPath()
                    };

                    for (JFXPanel panel : panels) {
                        ObservableList<String> stylesheets = panel
                                .getScene()
                                .getStylesheets();

                        for (int i = 0; i < previousStyleSheets.length; i++) {
                            int index = stylesheets.indexOf(previousStyleSheets[i]);

                            if (index != -1 && stylesheets.size() > index) {
                                stylesheets.set(index, newStyleSheets[i]);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates a panel that makes use of the global stylesheet.
     * <p>
     * {@link SkinManager#applySkin(Skin)}
     * must have been called before calling this method.
     *
     * @return a new JFXPanel that uses the global stylesheet
     */
    public static JFXPanel createPanel() {
        synchronized (SYNC_OBJECT) {
            if (currentSkin == null) {
                throw new IllegalStateException("A skin must be set before creating a panel");
            }
        }

        JFXPanel panel = new JFXPanel();

        runAndWait(panel, () -> {
            synchronized (SYNC_OBJECT) {
                Scene scene = new Scene(new BorderPane());

                scene.getStylesheets()
                        .addAll("https://fonts.googleapis.com/css?family=Overpass:300,400,600,700,800,900");

                panel.setScene(scene);

                panels.add(panel);
            }
        });

        return panel;
    }

    /**
     * Runs code on the JavaFX Application Thread,
     * and then updates the panel's scene so that
     * it gets resized in the case that components
     * have been added or removed from the panel.
     *
     * @param panel the panel that the Runnable operates on.
     *              This parameter may be null.
     * @param runnable the Runnable whose run method will be
     *                 executed on the JavaFX Application Thread
     */
    public static void runAndWait(JFXPanel panel, Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            CountDownLatch doneLatch = new CountDownLatch(1);

            Platform.runLater(() -> {
                try {
                    runnable.run();
                } finally {
                    doneLatch.countDown();
                }
            });

            try {
                doneLatch.await();
            } catch (InterruptedException ignored) {
            }
        }

		/*
			Hacky way of making it so the
			panel will resize when components
			have been added or removed.
		 */
        if (panel != null) {
            Scene scene = panel.getScene();
            panel.setScene(null);
            panel.setScene(scene);
        }
    }

    public static String getBaseStylesheetPath() {
        synchronized (SYNC_OBJECT) {
            return currentSkin.getBaseStylesheetPath();
        }
    }

    public static String getSettingsScreenStylesheetPath() {
        synchronized (SYNC_OBJECT) {
            return currentSkin.getSettingsScreenStylesheetPath();
        }
    }

    public static String getFrameTitleBarStylesheetPath() {
        synchronized (SYNC_OBJECT) {
            return currentSkin.getFrameTitleBarStylesheetPath();
        }
    }

    public static String getSettingsTitleBarStylesheetPath() {
        synchronized (SYNC_OBJECT) {
            return currentSkin.getSettingsTitleBarStylesheetPath();
        }
    }
}
