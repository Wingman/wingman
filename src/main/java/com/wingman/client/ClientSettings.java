package com.wingman.client;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientSettings {

    public static final Path HOME_DIR = Paths.get(System.getProperty("user.home")).resolve("Wingman");
    public static final Path PLUGINS_DIR = HOME_DIR.resolve("plugins");

    public static final Path APPLET_JAR_FILE = HOME_DIR.resolve("gamepack.jar");
    public static final Path MAPPINGS_FILE = HOME_DIR.resolve("mappings.json");
    public static final String LOGGING_FILE = HOME_DIR.resolve("wingman.log").toString();
    public static final Path SETTINGS_FILE = HOME_DIR.resolve("settings.json");

    public static final Dimension APPLET_INITIAL_SIZE = new Dimension(765, 503);

    /*
        Code for settings file loading:
     */

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Settings.class, (JsonDeserializer<Settings>) (json, typeOfT, context) -> {
                /*
                    Uses default field values for a field
                    if its value isn't set when de-serializing.

                    It's a hack to solve https://github.com/google/gson/issues/513.
                 */

                Settings settings = new Settings();

                ((JsonObject) json).entrySet().forEach(e -> {
                    try {
                        JsonPrimitive value = e
                                .getValue()
                                .getAsJsonPrimitive();

                        Field field = Settings.class.getDeclaredField(e.getKey());
                        field.setAccessible(true);

                        if (value.isBoolean()) {
                            field.set(settings, value.getAsBoolean());
                        } else if (value.isNumber()) {
                            Number number = value.getAsNumber();

                            if (field.getGenericType() == int.class) {
                                field.set(settings, number.intValue());
                            } else if (field.getGenericType() == long.class) {
                                field.set(settings, number.longValue());
                            } else if (field.getGenericType() == float.class) {
                                field.set(settings, number.floatValue());
                            } else if (field.getGenericType() == double.class) {
                                field.set(settings, number.doubleValue());
                            } else if (field.getGenericType() == short.class) {
                                field.set(settings, number.shortValue());
                            } else if (field.getGenericType() == byte.class) {
                                field.set(settings, number.byteValue());
                            }
                        } else if (value.isString()) {
                            field.set(settings, value.getAsString());
                        }
                    } catch (NoSuchFieldException | IllegalAccessException ignored) {
                    }
                });

                return settings;
            })
            .create();

    private static Settings settings = new Settings();

    private static void loadFromFile() {
        try {
            if (Files.exists(SETTINGS_FILE)) {
                try (JsonReader reader = new JsonReader(new BufferedReader(new FileReader(SETTINGS_FILE.toFile())))) {
                    settings = gson.fromJson(reader, Settings.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToFile() {
        try (JsonWriter writer = new JsonWriter(new BufferedWriter(new FileWriter(SETTINGS_FILE.toFile())))) {
            writer.setIndent("    "); // pretty-print
            gson.toJson(settings, Settings.class, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean areNotificationsEnabled() {
        return settings.notificationsEnabled;
    }

    public static void setNotificationsEnabled(boolean state) {
        settings.notificationsEnabled = state;
    }

    public static int getPreferredWorld() {
        return settings.preferredWorld;
    }

    public static void setPreferredWorld(int value) {
        settings.preferredWorld = value;
    }

    private static class Settings {
        private boolean notificationsEnabled = true;
        private int preferredWorld = 394;
    }

    static {
        loadFromFile();
    }
}
