package com.wingman.client.plugin.exceptions;

import java.text.MessageFormat;

/**
 * {@link PluginSetupException} is thrown when a plugin failed in setting up for whatever reason.
 */
public class PluginSetupException extends RuntimeException {

    public PluginSetupException(String pluginId, String message) {
        super(MessageFormat.format("{0} setup error: {1}", pluginId, message));
    }
}
