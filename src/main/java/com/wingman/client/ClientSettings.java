package com.wingman.client;

import com.google.common.collect.ImmutableMap;
import com.wingman.client.api.settings.PropertiesSettings;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClientSettings extends PropertiesSettings {

    public static final Path HOME_DIR = Paths.get(System.getProperty("user.home")).resolve("Wingman");
    public static final Path PLUGINS_DIR = HOME_DIR.resolve("plugins");
    public static final Path SETTINGS_DIR = HOME_DIR.resolve("settings");

    public static final Path APPLET_JAR_FILE = HOME_DIR.resolve("gamepack.jar");
    public static final Path MAPPINGS_FILE = HOME_DIR.resolve("mappings.json");
    public static final String LOGGING_FILE = HOME_DIR.resolve("wingman.log").toString();

    public static final Dimension APPLET_INITIAL_SIZE = new Dimension(765, 503);

    public static final String NOTIFICATIONS_ENABLED = "notifications_enabled";
    public static final String PREFERRED_WORLD = "preferred_world";

    public ClientSettings() throws IOException {
        super("Wingman.properties", "Wingman client settings");
        checkKeys();
    }

    private void checkKeys() {
        Map<String, Object> defaultProperties = ImmutableMap.<String, Object>builder()
                .put(NOTIFICATIONS_ENABLED, "true")
                .put(PREFERRED_WORLD, "301")
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
}
