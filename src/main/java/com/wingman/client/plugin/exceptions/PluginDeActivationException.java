package com.wingman.client.plugin.exceptions;

import java.text.MessageFormat;

/**
 * This is the superclass of all exceptions thrown while plugins are being loaded.
 *
 * It is aimed to wrap more specific error messages in a general error message displaying that an error has occurred.
 */
public class PluginDeActivationException extends RuntimeException {

    public PluginDeActivationException(String pluginId, Throwable cause) {
        super(MessageFormat.format("{0} deactivation failed", pluginId), cause);
    }
}
