package com.wingman.client.plugin;

import com.wingman.client.api.settings.PropertiesSettings;

import java.io.IOException;

/**
 * Created by mmccoy37 on 1/18/17.
 */
public class PluginSettings extends PropertiesSettings {

    /**
     * default constructor for creating a plugin states flatfile
     * @throws IOException if PropertiesSettings has IOException
     */
    public PluginSettings() throws IOException {
        super("WingmanPlugins.properties", "Activation state for user plugins");
    }

    @Override
    public void checkKeys() {
        super.checkKeys();
    }
}
