package com.wingman.client.plugin.exceptions;

import java.text.MessageFormat;

/**
 * {@link PluginLoadingException} is the superclass of all exceptions thrown while plugins are being loaded. <br>
 *
 * It is aimed to wrap more specific error messages in a general error message displaying that an error has occurred.
 */
public class PluginLoadingException extends RuntimeException {

    public PluginLoadingException(String pluginId, String message) {
        super(MessageFormat.format("{0} loading error: {1}", pluginId, message));
    }
}
