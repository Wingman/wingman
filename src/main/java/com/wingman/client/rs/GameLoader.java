package com.wingman.client.rs;

import com.google.common.base.Throwables;
import com.wingman.client.Settings;
import com.wingman.client.api.generated.Static;
import com.wingman.client.classloader.TransformingClassLoader;
import com.wingman.client.plugin.PluginManager;
import com.wingman.client.rs.listeners.CanvasMouseListener;
import com.wingman.client.ui.Client;

import java.applet.Applet;
import java.awt.*;
import java.net.MalformedURLException;

public class GameLoader {

    public static Applet applet = null;

    public GameLoader() {
        PluginManager.findAndSetupPlugins();

        System.out.println("Loading the game");
        try {
            TransformingClassLoader classLoader = (TransformingClassLoader) getClass().getClassLoader();

            classLoader.addURL(Settings.APPLET_JAR_FILE.toUri().toURL());

            applet = (Applet) classLoader.loadClass("client").newInstance();
            applet.setStub(new GameAppletStub());

            applet.setLayout(new BorderLayout() {
                @Override
                public Dimension minimumLayoutSize(Container target) {
                    return Settings.APPLET_INITIAL_SIZE;
                }

                @Override
                public Dimension preferredLayoutSize(Container target) {
                    if (target.getWidth() == 0 || target.getHeight() == 0) {
                        return Settings.APPLET_INITIAL_SIZE;
                    }
                    return target.getSize();
                }
            });
            applet.setSize(Settings.APPLET_INITIAL_SIZE);

            applet.init();
            applet.start();

            Client.framePanel.removeAll();
            Client.framePanel.add(applet, BorderLayout.CENTER);
            Client.framePanel.add(Client.sideBarBox, BorderLayout.EAST);
            Client.frame.pack();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    PluginManager.activatePlugins();
                }
            }).start();

            while (Static.getCanvas() == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Throwables.propagate(e);
                }
            }

            Static.getCanvas().addMouseListener(new CanvasMouseListener());
        } catch (MalformedURLException | ClassNotFoundException
                | InstantiationException | IllegalAccessException e) {
            Throwables.propagate(e);
        }
    }
}
