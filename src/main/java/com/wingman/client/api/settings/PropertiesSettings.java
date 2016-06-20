package com.wingman.client.api.settings;

import com.wingman.client.ClientSettings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesSettings extends Settings {

    public File file;
    public Properties properties;
    public String fileComments;

    public PropertiesSettings(String settingsFileName, String fileComments) throws IOException {
        this.file = ClientSettings.SETTINGS_DIR.resolve(settingsFileName).toFile();
        if (!this.file.exists()) {
            if (!this.file.createNewFile()) {
                throw new IOException("Couldn't create file " + file);
            }
        }
        this.fileComments = fileComments;
        this.properties = new Properties();
        this.properties.load(new FileReader(file));
        checkKeys();
    }

    public void checkKeys() {
    }

    @Override
    public void update(String key, Object value) {
        properties.put(key, value);
    }

    @Override
    public String get(String key) {
        return (String) properties.get(key);
    }

    @Override
    public void save() {
        try {
            properties.store(new FileOutputStream(file), fileComments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getBoolean(String key) {
        return get(key).equals("true");
    }

    public int getInteger(String key) {
        return Integer.parseInt(get(key));
    }
}
