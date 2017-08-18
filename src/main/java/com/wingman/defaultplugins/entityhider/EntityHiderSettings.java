package com.wingman.defaultplugins.entityhider;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wingman.client.api.plugin.PluginHelper;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Optional;

public class EntityHiderSettings {

    private static final String FILE_NAME = "settings.json";

    private PluginHelper helper;
    private Gson gson = new GsonBuilder()
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

    private Settings settings = new Settings();

    public EntityHiderSettings(PluginHelper helper) {
        this.helper = helper;
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            Optional<InputStream> fileStream = helper.getResourceStream(FILE_NAME);

            if (fileStream.isPresent()) {
                try (JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(fileStream.get())))) {
                    settings = gson.fromJson(reader, Settings.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        try (JsonWriter writer = new JsonWriter(new BufferedWriter(new OutputStreamWriter(
                helper.getResourceOutputStream(FILE_NAME), "utf-8")))) {
            writer.setIndent("    "); // pretty-print
            gson.toJson(settings, Settings.class, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean isHidingPlayers() {
        return settings.hidingPlayers;
    }

    public synchronized void setHidingPlayers(boolean state) {
        this.settings.hidingPlayers = state;
    }

    public synchronized boolean isHidingLocalPlayer() {
        return settings.hidingLocalPlayer;
    }

    public synchronized void setHidingLocalPlayer(boolean state) {
        this.settings.hidingLocalPlayer = state;
    }

    public synchronized boolean isHidingNPCs() {
        return settings.hidingNPCs;
    }

    public synchronized void setHidingNPCs(boolean state) {
        this.settings.hidingNPCs = state;
    }

    public synchronized boolean isHidingPlayers2D() {
        return settings.hidingPlayers2D;
    }

    public synchronized void setHidingPlayers2D(boolean state) {
        this.settings.hidingPlayers2D = state;
    }

    public synchronized boolean isHidingLocalPlayer2D() {
        return settings.hidingLocalPlayer2D;
    }

    public synchronized void setHidingLocalPlayer2D(boolean state) {
        this.settings.hidingLocalPlayer2D = state;
    }

    public synchronized boolean isHidingNPCs2D() {
        return settings.hidingNPCs2D;
    }

    public synchronized void setHidingNPCs2D(boolean state) {
        this.settings.hidingNPCs2D = state;
    }

    private class Settings {
        private boolean hidingPlayers = false;
        private boolean hidingLocalPlayer = false;
        private boolean hidingNPCs = false;

        private boolean hidingPlayers2D = false;
        private boolean hidingLocalPlayer2D = false;
        private boolean hidingNPCs2D = false;
    }
}
