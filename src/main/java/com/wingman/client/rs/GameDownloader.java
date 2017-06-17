package com.wingman.client.rs;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.wingman.client.ClientSettings;
import com.wingman.client.api.net.HttpClient;
import com.wingman.client.ui.Client;
import com.wingman.client.ui.skin.SkinManager;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
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
    private String pageSource;
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

        int world = getWorldFromSettings();

        updateMessage("Downloading game info from world 3" + world + "..");

        pageSource = getPageSource(world, false);

        Matcher archiveMatcher = Pattern
                .compile("initial_jar=([\\S]+)")
                .matcher(pageSource);

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

    /**
     * Downloads the page source from the official OldSchool RuneScape web client page.
     *
     * @param world the world to load page source from
     * @param failedOnce {@code true} if the client should directly fall back to the default world if the request fails,
     *                   {@code false} if it should retry once before falling back to the default world
     * @return the page source of the OldSchool RuneScape web client page
     */
    private String getPageSource(int world, boolean failedOnce) {
        runeScapeUrl = "http://oldschool" + world + ".runescape.com/";

        try {
            return httpClient
                    .downloadUrlSync(runeScapeUrl + "jav_config.ws")
                    .body()
                    .string();
        } catch (IOException e) {
            if (!failedOnce) {
                return getPageSource(1, true);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Gets the user-specified world saved in the client settings.
     * <p>
     * More specifically, tries to parse the {@link ClientSettings#properties} value
     * for the key {@link ClientSettings#PREFERRED_WORLD}.
     * <p>
     * The parsed value must be in the format "358".
     * With other words, the value cannot be "58" for world 358. That will be parsed as the world "308".
     * <p>
     * If the parsing of the value as a number fails, the default world "1" (301) will be returned.
     *
     * @return the saved (and preferred) world from {@link ClientSettings},
     *         or 1 (world 301) if the lookup failed
     */
    private int getWorldFromSettings() {
        try {
            return Integer.parseInt(Client
                    .clientSettings
                    .get(ClientSettings.PREFERRED_WORLD)
                    .substring(1));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return 1;
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

    public String getPageSource() {
        return pageSource;
    }

    public String getArchiveName() {
        return archiveName;
    }
}
