package com.wingman.defaultplugins.grounditems;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wingman.client.api.plugin.PluginHelper;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Optional;

public class GroundItemsSettings {

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

                        if (value.isBoolean()) {
                            field.set(settings, value.getAsBoolean());
                        } else if (value.isNumber()) {
                            field.set(settings, value.getAsNumber());
                        }else if (value.isString()) {
                            field.set(settings, value.getAsString());
                        }
                    } catch (NoSuchFieldException | IllegalAccessException ignored) {
                    }
                });

                return settings;
            })
            .create();

    private Settings settings = new Settings();

    public GroundItemsSettings(PluginHelper helper) {
        this.helper = helper;
        loadFromFile();
        System.out.println(getExpensive());
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

    public boolean isEnabled() {
        return settings.enabled;
    }

    public void setEnabled(boolean state) {
        this.settings.enabled = state;
    }

    public boolean isMiniMapDotEnabled() {
        return settings.miniMapDotEnabled;
    }

    public void setMiniMapDotEnabled(boolean state) {
        this.settings.miniMapDotEnabled = state;
    }

    public int getExpensive() {
        return settings.expensive;
    }

    public void setExpensive(int value) {
        this.settings.expensive = value;
    }

    public int getVeryExpensive() {
        return settings.veryExpensive;
    }

    public void setVeryExpensive(int value) {
        this.settings.veryExpensive = value;
    }

    public String getVeryExpensiveColor() {
        return settings.veryExpensiveColor;
    }

    public void setVeryExpensiveColor(String veryExpensiveColor) {
        this.settings.veryExpensiveColor = veryExpensiveColor;
    }

    public String getExpensiveColor() {
        return settings.expensiveColor;
    }

    public void setExpensiveColor(String expensiveColor) {
        this.settings.expensiveColor = expensiveColor;
    }

    public String getCheapColor() {
        return settings.cheapColor;
    }

    public void setCheapColor(String cheapColor) {
        this.settings.cheapColor = cheapColor;
    }

    public String getUnTradeableColor() {
        return settings.unTradeableColor;
    }

    public void setUnTradeableColor(String unTradeableColor) {
        this.settings.unTradeableColor = unTradeableColor;
    }

    private class Settings {
        private boolean enabled = true;
        private boolean miniMapDotEnabled = true;

        private int expensive = 50_000;
        private int veryExpensive = 250_000;

        private String veryExpensiveColor = "#D4AF37";
        private String expensiveColor = "#32CD32";
        private String cheapColor = "#FFFFFF";
        private String unTradeableColor = "#FF1493";
    }
}
