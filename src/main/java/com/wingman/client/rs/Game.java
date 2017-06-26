package com.wingman.client.rs;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.generated.Static;
import com.wingman.client.api.mapping.MappingsHelper;
import com.wingman.client.classloader.TransformingClassLoader;
import com.wingman.client.rs.listeners.CanvasMouseListener;
import com.wingman.client.rs.listeners.CanvasMouseWheelListener;
import com.wingman.client.settings.ClientSettings;

import java.applet.Applet;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Game {

    private Applet applet;

    public Game() {
        Object monitor = new Object();

        GameDownloader downloader = new GameDownloader() {
            @Override
            protected void done() {
                synchronized (monitor) {
                    monitor.notify();
                }
            }
        };

        String runeScapeUrl;
        String pageSource;
        String archiveName;

        synchronized (monitor) {
            try {
                monitor.wait();

                runeScapeUrl = downloader.getRuneScapeUrl();
                pageSource = downloader.getPageSource();
                archiveName = downloader.getArchiveName();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            boolean hasCorrectMappings = false;

            try {
                /*
                    Makes sure that the local mappings
                    can be used with the local gamepack.

                    If they can't, turn off features
                    that depend on the mappings.
                 */

                HashCode gamepackHashCode = Files
                        .hash(ClientSettings.APPLET_JAR_FILE.toFile(), Hashing.md5());

                hasCorrectMappings = MappingsHelper
                        .gamepackHash
                        .equals(gamepackHashCode);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!hasCorrectMappings) {
                System.out.println("Mappings are outdated, turning off features depending on them..");
            }

            TransformingClassLoader classLoader = new TransformingClassLoader(
                    new URL[]{ClientSettings.APPLET_JAR_FILE.toUri().toURL()},
                    this.getClass().getClassLoader()
            );

            Object clientInstance = classLoader
                    .loadClass("client")
                    .newInstance();

            if (hasCorrectMappings) {
                GameAPI.getterInstance = (Static) clientInstance;
                GameAPI.Unsafe.setterInstance = (Static.Unsafe) clientInstance;
            }

            applet = (Applet) clientInstance;
            applet.setStub(new GameAppletStub(runeScapeUrl, pageSource, archiveName));

            applet.setLayout(new BorderLayout() {
                @Override
                public Dimension minimumLayoutSize(Container target) {
                    return ClientSettings.APPLET_INITIAL_SIZE;
                }

                @Override
                public Dimension preferredLayoutSize(Container target) {
                    if (target.getWidth() == 0 || target.getHeight() == 0) {
                        return ClientSettings.APPLET_INITIAL_SIZE;
                    }
                    return target.getSize();
                }
            });
            applet.setSize(ClientSettings.APPLET_INITIAL_SIZE);

            applet.init();
            applet.start();

            if (hasCorrectMappings) {
                while (GameAPI.getClientInstance().getCanvas() == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                GameAPI.getClientInstance().getCanvas()
                        .addMouseListener(new CanvasMouseListener());

                GameAPI.getClientInstance().getCanvas()
                        .addMouseWheelListener(new CanvasMouseWheelListener());
            }
        } catch (MalformedURLException | ClassNotFoundException
                | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Applet getApplet() {
        return applet;
    }
}
