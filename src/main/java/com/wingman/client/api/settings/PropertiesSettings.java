package com.wingman.client.api.settings;

import com.wingman.client.ClientSettings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesSettings {

    public File file;
    public Properties properties;
    public String fileComments;

    public PropertiesSettings(String settingsFileName, String fileComments) throws IOException {
        this.file = ClientSettings.SETTINGS_DIR
                .resolve(settingsFileName)
                .toFile();

        if (!this.file.exists()
                && !this.file.createNewFile()) {
            throw new IOException("Couldn't create file " + file);
        }

        this.fileComments = fileComments;

        this.properties = new Properties();
        this.properties.load(new FileReader(file));
    }

    public void update(String key, Object value) {
        properties.put(key, value);
    }

    public void save() {
        try {
            properties.store(new FileOutputStream(file), fileComments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return (String) properties.get(key);
    }

    public boolean getBoolean(String key) {
        return "true".equals(get(key));
    }

    public int getInteger(String key) {
        return Integer.parseInt(get(key));
    }
}
