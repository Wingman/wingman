package com.wingman.client;

import com.google.common.collect.ImmutableMap;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ClientSettings {

    public static final Path HOME_DIR = Paths.get(System.getProperty("user.home")).resolve("Wingman");
    public static final Path PLUGINS_DIR = HOME_DIR.resolve("plugins");
    public static final Path SETTINGS_DIR = HOME_DIR.resolve("settings");

    public static final Path APPLET_JAR_FILE = HOME_DIR.resolve("gamepack.jar");
    public static final String LOGGING_FILE = HOME_DIR.resolve("wingman.log").toString();
    public static final File CLIENT_SETTINGS_FILE = HOME_DIR.resolve("wingman.properties").toFile();

    public static final Dimension APPLET_INITIAL_SIZE = new Dimension(765, 503);

    public static final String NOTIFICATIONS_ENABLED = "notifications_enabled";
    public static final String PREFERRED_WORLD = "preferred_world";

    private Properties properties = new Properties();

    public ClientSettings() {
        if (CLIENT_SETTINGS_FILE.exists()) {
            try {
                properties.load(new FileReader(CLIENT_SETTINGS_FILE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> defaultProperties = ImmutableMap.<String, Object>builder()
                .put(NOTIFICATIONS_ENABLED, "true")
                .put(PREFERRED_WORLD, "311")
                .build();

        if (properties.isEmpty()) {
            for (Map.Entry<String, Object> e : defaultProperties.entrySet()) {
                properties.put(e.getKey(), e.getValue());
            }
        } else {
            for (Map.Entry<String, Object> e : defaultProperties.entrySet()) {
                if (!properties.containsKey(e.getKey())) {
                    properties.put(e.getKey(), e.getValue());
                }
            }

            Set<String> keysToRemove = new HashSet<>();
            for (Map.Entry<Object, Object> e : properties.entrySet()) {
                String key = (String) e.getKey();
                if (!defaultProperties.containsKey(key)) {
                    keysToRemove.add(key);
                }
            }
            for (String k : keysToRemove) {
                properties.remove(k);
            }
        }
    }

    public void save() {
        try {
            properties.store(new FileWriter(CLIENT_SETTINGS_FILE), "Wingman client settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(String key, Object value) {
        properties.put(key, value);
    }

    public String get(String key) {
        return (String) properties.get(key);
    }

    public boolean getBoolean(String key) {
        return properties.get(key).equals("true");
    }

    public int getInteger(String key) {
        return Integer.parseInt((String) properties.get(key));
    }
}
