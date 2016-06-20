package com.wingman.client.rs;

import com.google.common.base.Throwables;
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
 * {@link GameDownloader} handles keeping the RuneScape game pack up to date. <br>
 * It also downloads the RuneScape page source for feeding the game applet with the expected parameters.
 */
public class GameDownloader extends SwingWorker<Void, Integer>{

    static String runeScapeUrl = null;
    static String pageSource = null;
    static String archiveName = null;
    private static int remoteArchiveSize = 0;

    private static StartProgressBar progressBar = new StartProgressBar();

    public GameDownloader() {

        // Make sure GUI modifications take place on the Event Dispatch Thread.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setMode(StartProgressBar.Mode.CHECKING_FOR_UPDATES);
                Client.framePanel.add(progressBar, BorderLayout.SOUTH);
                Client.framePanel.validate();
            }
        });

        execute();
    }

    @Override
    protected Void doInBackground() throws Exception {
        pageSource = getPageSource(getWorldFromSettings(), false);

        Matcher archiveMatcher = Pattern
                .compile("initial_jar=([\\S]+)")
                .matcher(pageSource);

        if (archiveMatcher.find()) {
            archiveName = archiveMatcher.group(1);

            if (!checkGamePackUpToDate()) {
                startUpdatingGamePack();
            } else {
                // GUI modifications should take place on the Event Dispatch Thread.
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setMode(StartProgressBar.Mode.NO_UPDATES);
                    }
                });
                new GameLoader();
            }
        }
        return null;
    }

    @Override
    protected void process(List<Integer> chunks) {
        super.process(chunks);
        if(chunks.size() > 0){
            progressBar.setValue(chunks.get(0));
        }
    }

    @Override
    protected void done() {
        super.done();
        // If this method is called and the last mode was DOWNLOADING,
        // assume that downloading is finished.
        if(progressBar.getMode() == StartProgressBar.Mode.DOWNLOADING){
            progressBar.setMode(StartProgressBar.Mode.DOWNLOADING_FINISHED);
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
            return HttpClient
                    .downloadUrlSync(runeScapeUrl + "jav_config.ws")
                    .body()
                    .string();
        } catch (IOException e) {
            if (!failedOnce) {
                String errorMessage = "Wingman couldn't load the world it's configured to use. Falling back onto W311.";
                System.out.println(errorMessage);

                JLabel errorLabel = new JLabel(errorMessage, SwingConstants.CENTER);
                Client.framePanel.add(errorLabel, BorderLayout.SOUTH);
                Client.frame.revalidate();

                return getPageSource(11, true);
            } else {
                throw Throwables.propagate(e);
            }
        }
    }

    /**
     * Tries to parse the {@link ClientSettings#properties} value for the key {@link ClientSettings#PREFERRED_WORLD}. <br>
     * If the parsed value begins with 3, the 3 will be omitted before returning the value. <br>
     * If the parsing of the value as a number fails, the default world "11" (311) will be returned.
     *
     * @return the saved (and preferred) world from {@link ClientSettings},
     *         or 11 (world 311) if the lookup failed
     */
    private int getWorldFromSettings() {
        int world = 11;

        try {
            String tempWorld = Client.clientSettings.get(ClientSettings.PREFERRED_WORLD);

            System.out.println("Attempting to load world " + tempWorld);

            if (tempWorld.charAt(0) == '3') {
                world = Integer.parseInt(tempWorld.substring(1));
            } else {
                world = Integer.parseInt(tempWorld);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return world;
    }

    /**
     * Checks if the size of the existing game pack equals what is returned as Content-Length of the latest game pack. <br>
     * The method does not include any actual revision checks, but it is expected that the size will change between revisions.
     *
     * @return {@code true} if the game pack is of the latest version,
     *         {@code false} if it is not
     */
    private static boolean checkGamePackUpToDate() {
        try {
            System.out.println("Checking for gamepack updates");

            Response response = HttpClient
                    .downloadUrlSync(runeScapeUrl + archiveName);

            remoteArchiveSize = Integer
                    .parseInt(response.header("Content-Length"));

            response.body().close();

            return ClientSettings.APPLET_JAR_FILE
                    .toFile()
                    .length() == remoteArchiveSize;
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    private void startUpdatingGamePack() {
        System.out.println(MessageFormat.format("Updating the gamepack, remote size: {0}, remote archive name: {1}", remoteArchiveSize, archiveName));

        progressBar.setMinimum(0);
        progressBar.setMaximum(remoteArchiveSize);
        progressBar.setMode(StartProgressBar.Mode.DOWNLOADING);

        try {
            Request request = HttpClient
                    .getRealisticRequestBuilder()
                    .url(runeScapeUrl + archiveName)
                    .build();

            Response response = HttpClient.httpClient
                    .newCall(request)
                    .execute();

            ResponseBody responseBody = response.body();

            if (response.isSuccessful()) {
                try (InputStream input = responseBody.byteStream()) {
                    if (input != null) {
                        FileOutputStream output = new FileOutputStream(ClientSettings.APPLET_JAR_FILE.toFile());

                        final int[] totalRead = {0};
                        final int[] read = {0};
                        byte[] data = new byte[4096];

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

                System.out.println("Done updating the gamepack");

                new GameLoader();
            }

            responseBody.close();
        } catch (IOException e) {
            Throwables.propagate(e);
        }

    }
}
