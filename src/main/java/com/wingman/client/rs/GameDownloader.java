package com.wingman.client.rs;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.wingman.client.ClientSettings;
import com.wingman.client.api.net.HttpClient;
import com.wingman.client.api.net.world.WorldInfo;
import com.wingman.client.api.net.world.WorldInfoDownloader;
import com.wingman.client.api.ui.settingscreen.SettingsItem;
import com.wingman.client.ui.Client;
import com.wingman.client.ui.skin.SkinManager;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles keeping the RuneScape gamepack up to date.
 * <p>
 * It also downloads the RuneScape page source for feeding the game applet with the expected parameters.
 */
public class GameDownloader extends Task<Double> {

    private static final int DOWNLOAD_BUFFER_SIZE = 4096;
    private static final int BYTES_IN_A_KILOBYTE = 1000;

    private HttpClient httpClient = new HttpClient();

    private ProgressBar progressBar = new ProgressBar();
    private Label progressLabel = new Label();

    private String runeScapeUrl;
    private String configSource;
    private String archiveName;
    private int remoteArchiveSize;

    public GameDownloader() {
        SwingUtilities.invokeLater(() -> {
            JFXPanel progressBarPanel = SkinManager.createPanel();

            SkinManager.runAndWait(progressBarPanel, () -> {
                BorderPane progressBarPanelPane = (BorderPane) progressBarPanel
                        .getScene()
                        .getRoot();

                StackPane stackPane = new StackPane();

                stackPane.getChildren()
                        .addAll(progressBar, progressLabel);

                progressBarPanelPane.setCenter(stackPane);

                progressBar
                        .progressProperty()
                        .bind(this.valueProperty());

                progressLabel
                        .textProperty()
                        .bind(this.messageProperty());
            });

            Client.framePanel.add(progressBarPanel, BorderLayout.SOUTH);
            Client.framePanel.validate();
        });

        new Thread(this).start();
    }

    @Override
    protected Double call() throws Exception {
        updateValue(0D);

        WorldInfo world = getPreferredWorld();

        updateMessage("Downloading game info from world " + world.id + "..");

        configSource = getConfigSource(world.host);

        Matcher archiveMatcher = Pattern
                .compile("initial_jar=([\\S]+)")
                .matcher(configSource);

        if (archiveMatcher.find()) {
            archiveName = archiveMatcher.group(1);

            if (!checkGamePackUpToDate()) {
                updateMessage("Starting download of Oldschool Runescape..");

                startUpdatingGamePack();

                updateMessage("Download complete. Launching Oldschool Runescape..");
            } else {
                updateMessage("No updates detected. Launching Oldschool Runescape..");

                updateValue(ProgressBar.INDETERMINATE_PROGRESS);
            }
        }

        return ProgressBar.INDETERMINATE_PROGRESS;
    }

    private WorldInfo getPreferredWorld() throws IOException {
        SettingsItem settingsItem = new SettingsItem("Preferred game world");

        ComboBox<Integer> comboBox = new ComboBox<>();

        List<WorldInfo> worlds = WorldInfoDownloader
                .downloadWorldInfo();

        // Sort the world list by world ID
        worlds.sort((Comparator.comparingInt(o -> o.id)));

        WorldInfo preferredWorld = null;

        for (WorldInfo world : worlds) {
            comboBox.getItems()
                    .add(world.id);

            if (world.id == ClientSettings.getPreferredWorld()) {
                preferredWorld = world;
            }
        }

        if (preferredWorld == null) {
            // Use a random world in the list if the
            // currently preferred world is offline
            Collections.shuffle(worlds);
            preferredWorld = worlds.get(0);
        }

        comboBox.setValue(ClientSettings.getPreferredWorld());
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            ClientSettings.setPreferredWorld(newValue);
            ClientSettings.saveToFile();
        });

        settingsItem.add(comboBox);
        Client.clientSettingsSection.add(settingsItem);

        return preferredWorld;
    }

    /**
     * Downloads the page source from the official OldSchool RuneScape web client page.
     *
     * @param host the host to load page source from
     * @return the page source of the OldSchool RuneScape web client page
     */
    private String getConfigSource(String host) throws IOException {
        runeScapeUrl = "http://" + host + "/";

        return httpClient
                .downloadUrlSync(runeScapeUrl + "jav_config.ws")
                .body()
                .string();
    }

    /**
     * Checks if the size of the existing game pack equals what is returned as Content-Length of the latest game pack.
     * <p>
     * The method does not include any actual revision checks,
     * but it is expected that the size will change between revisions.
     *
     * @return {@code true} if the locally saved game pack is of the latest version,
     *         {@code false} otherwise
     */
    private boolean checkGamePackUpToDate() throws IOException {
        remoteArchiveSize = httpClient
                .getContentLength(runeScapeUrl + archiveName);

        return ClientSettings.APPLET_JAR_FILE
                .toFile()
                .length() == remoteArchiveSize;
    }

    private void startUpdatingGamePack() throws IOException {
        boolean downloadSucceeded = false;

        do {
            Request request = httpClient
                    .getRequestBuilder()
                    .url(runeScapeUrl + archiveName)
                    .build();

            Response response = httpClient
                    .newCall(request)
                    .execute();

            ResponseBody responseBody = response.body();

            if (response.isSuccessful()) {
                try (InputStream input = responseBody.byteStream()) {
                    if (input != null) {
                        FileOutputStream output = new FileOutputStream(ClientSettings.APPLET_JAR_FILE.toFile());

                        int totalRead = 0;

                        int read;
                        byte[] data = new byte[DOWNLOAD_BUFFER_SIZE];

                        long startTime = System.nanoTime();
                        int readSinceStartTime = 0;
                        int currentSpeed = 0;

                        while ((read = input.read(data, 0, data.length)) != -1) {
                            totalRead += read;
                            output.write(data, 0, read);

                            readSinceStartTime += read;

                            long currentTime = System.nanoTime();

                            long timeDelta = currentTime - startTime;
                            // 1E9 = the amount of nanoseconds in one second
                            if (timeDelta >= 1E9) {
                                // Convert bytes per nanosecond to kilobytes per second
                                // and get the gradient
                                currentSpeed = (int) Math.ceil(readSinceStartTime / (timeDelta / 1E6));

                                startTime = currentTime;
                                readSinceStartTime = 0;
                            }

                            double doneFraction = totalRead / (double) remoteArchiveSize;

                            updateValue(doneFraction);

                            updateMessage(MessageFormat
                                    .format("Downloading Oldschool Runescape - {0}% done ({1}/{2} kB at {3} kB/s)",
                                            Math.ceil(doneFraction * 100),
                                            Math.ceil(totalRead / (double) BYTES_IN_A_KILOBYTE),
                                            Math.ceil(remoteArchiveSize / (double) BYTES_IN_A_KILOBYTE),
                                            currentSpeed));
                        }

                        if (totalRead == remoteArchiveSize) {
                            downloadSucceeded = true;
                        } else {
                            updateMessage(MessageFormat.format(
                                    "Downloading the game failed, trying again in 5 seconds. " +
                                            "Reason: Malformed response (received {0}/{1} bytes)",
                                    totalRead, remoteArchiveSize));

                            try {
                                Thread.sleep(5 * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        input.close();
                        output.close();
                    } else {
                        updateMessage("Downloading the game failed, trying again in 5 seconds. " +
                                "Reason: Response stream was null");

                        try {
                            Thread.sleep(5 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                updateMessage("Downloading the game failed, trying again in 5 seconds. " +
                        "Reason: Response code was " + response.code());

                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            responseBody.close();
        } while (!downloadSucceeded);
    }

    public String getRuneScapeUrl() {
        return runeScapeUrl;
    }

    public String getConfigSource() {
        return configSource;
    }

    public String getArchiveName() {
        return archiveName;
    }
}
