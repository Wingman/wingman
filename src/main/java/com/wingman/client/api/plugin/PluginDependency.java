package com.wingman.client.api.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use this interface to annotate the main plugin class of a plugin that depends another plugin. <br>
 * Any class in the plugin package can utilize the depended plugin. <br>
 * Wrap multiple {@link PluginDependency} inside {@link PluginDependencies}.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginDependency {

    String id();

    String version();
}
