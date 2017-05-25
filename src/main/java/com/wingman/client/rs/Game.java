package com.wingman.client.rs;

import com.wingman.client.ClientSettings;
import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.generated.Static;
import com.wingman.client.classloader.TransformingClassLoader;
import com.wingman.client.rs.listeners.CanvasMouseListener;
import com.wingman.client.rs.listeners.CanvasMouseWheelListener;

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
                super.done();

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
            TransformingClassLoader classLoader = new TransformingClassLoader(
                    new URL[]{ClientSettings.APPLET_JAR_FILE.toUri().toURL()},
                    this.getClass().getClassLoader()
            );

            Object clientInstance = classLoader
                    .loadClass("client")
                    .newInstance();

            GameAPI.getterInstance = (Static) clientInstance;
            GameAPI.Unsafe.setterInstance = (Static.Unsafe) clientInstance;

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
        } catch (MalformedURLException | ClassNotFoundException
                | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Applet getApplet() {
        return applet;
    }
}
