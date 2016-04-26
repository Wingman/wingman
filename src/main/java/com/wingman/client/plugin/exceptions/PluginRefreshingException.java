package com.wingman.client.plugin.exceptions;

import java.text.MessageFormat;

/**
 * {@link PluginRefreshingException} is thrown when a plugin failed in refreshing for whatever reason.
 */
public class PluginRefreshingException extends RuntimeException {

    public PluginRefreshingException(String pluginId, String message) {
        super(MessageFormat.format("{0} refreshing error: {1}", pluginId, message));
    }
}
