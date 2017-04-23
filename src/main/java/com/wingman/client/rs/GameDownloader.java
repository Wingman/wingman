package com.wingman.client.rs;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.wingman.client.ClientSettings;
import com.wingman.client.api.net.HttpClient;
import com.wingman.client.ui.Client;
import com.wingman.client.ui.components.StartProgressBar;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles keeping the RuneScape gamepack up to date.
 * <p>
 * It also downloads the RuneScape page source for feeding the game applet with the expected parameters.
 */
public class GameDownloader extends SwingWorker<Void, Integer> {

    private static final int DOWNLOAD_BUFFER_SIZE = 4096;

    private HttpClient httpClient = new HttpClient();
    private StartProgressBar progressBar = new StartProgressBar();

    private String runeScapeUrl;
    private String pageSource;
    private String archiveName;
    private int remoteArchiveSize;

    public GameDownloader() {
        // Make sure GUI modifications take place on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            progressBar.setMode(StartProgressBar.Mode.CHECKING_FOR_UPDATES);
            Client.framePanel.add(progressBar, BorderLayout.SOUTH);
            Client.framePanel.validate();
        });

        execute();
    }

    @Override
    protected Void doInBackground() throws IOException {
        pageSource = getPageSource(getWorldFromSettings(), false);

        Matcher archiveMatcher = Pattern
                .compile("initial_jar=([\\S]+)")
                .matcher(pageSource);

        if (archiveMatcher.find()) {
            archiveName = archiveMatcher.group(1);

            if (!checkGamePackUpToDate()) {
                startUpdatingGamePack();
            } else {
                SwingUtilities.invokeLater(() ->
                        progressBar.setMode(StartProgressBar.Mode.NO_UPDATES));
            }
        }

        return null;
    }

    @Override
    protected void process(List<Integer> chunks) {
        if (!chunks.isEmpty()) {
            progressBar.setValue(chunks.get(0));
        }
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
            String tempWorld = Client.clientSettings.get(ClientSettings.PREFERRED_WORLD);

            System.out.println("Attempting to load world " + tempWorld);

            return Integer.parseInt(tempWorld.substring(1));
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
        Response response = httpClient
                .downloadUrlSync(runeScapeUrl + archiveName);

        remoteArchiveSize = Integer
                .parseInt(response.header("Content-Length"));

        response.body().close();

        return ClientSettings.APPLET_JAR_FILE
                .toFile()
                .length() == remoteArchiveSize;
    }

    private void startUpdatingGamePack() throws IOException {
        System.out.println(MessageFormat.format(
                "Updating the gamepack, " +
                        "remote size: {0}, " +
                        "remote archive name: {1}",
                remoteArchiveSize,
                archiveName));

        progressBar.setMinimum(0);
        progressBar.setMaximum(remoteArchiveSize);
        progressBar.setMode(StartProgressBar.Mode.DOWNLOADING);

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

                    final int[] totalRead = {0};
                    final int[] read = {0};
                    byte[] data = new byte[DOWNLOAD_BUFFER_SIZE];

                    while ((read[0] = input.read(data, 0, data.length)) != -1) {
                        totalRead[0] += read[0];
                        output.write(data, 0, read[0]);

                        publish(totalRead[0]);
                    }

                    publish(remoteArchiveSize);

                    input.close();
                    output.close();
                }
            }

            responseBody.close();
        } else {
            responseBody.close();
            throw new IOException("Failed to download the gamepack");
        }
    }

    @Override
    protected void done() {
        // If this method is called and the last mode was DOWNLOADING,
        // assume that downloading is finished.
        if (progressBar.getMode() == StartProgressBar.Mode.DOWNLOADING) {
            progressBar.setMode(StartProgressBar.Mode.DOWNLOADING_FINISHED);
        }
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
