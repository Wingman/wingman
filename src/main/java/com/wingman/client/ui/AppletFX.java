package com.wingman.client.ui;

/*
 * Copyright 2017, UniquePassive <https://github.com/uniquepassive>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import com.sun.javafx.application.PlatformImpl;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class AppletFX {

    private static final Object SYNC_OBJECT = new Object();

    private static List<JFXPanel> panels = new ArrayList<>();

    private static String stylesheetPath;

    /**
     * Creates a panel that makes use of the global stylesheet.
     * <p>
     * {@link AppletFX#setGlobalStylesheet(String)}
     * must have been called before calling this method.
     *
     * @return a new JFXPanel that uses the global stylesheet
     */
    public static JFXPanel createPanel() {
        if (stylesheetPath == null) {
            throw new IllegalStateException("A global stylesheet must be set before creating a panel");
        }

        JFXPanel panel = new JFXPanel();

        PlatformImpl.runAndWait(() -> {
            synchronized (SYNC_OBJECT) {
                Scene scene = new Scene(new BorderPane());
                scene.getStylesheets().add(stylesheetPath);

                panel.setScene(scene);

                panels.add(panel);
            }
        });

        return panel;
    }

    /**
     * Sets the global stylesheet that is
     * applied to all new and existing panels.
     *
     * @param path the file path to the stylesheet
     */
    public static void setGlobalStylesheet(String path) {
        synchronized (SYNC_OBJECT) {
            String previousStyleSheet = stylesheetPath;
            stylesheetPath = path;

            for (JFXPanel panel : panels) {
                ObservableList<String> stylesheets = panel
                        .getScene()
                        .getStylesheets();

                int index = stylesheets.indexOf(previousStyleSheet);

                if (index != -1 && stylesheets.size() > index) {
                    stylesheets.set(index, path);
                }
            }
        }
    }

    /**
     * @return the global stylesheet file path,
     *         or {@code null} if it hasn't been set
     */
    public static String getGlobalStylesheet() {
        return stylesheetPath;
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
        PlatformImpl.runAndWait(runnable);

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
}
