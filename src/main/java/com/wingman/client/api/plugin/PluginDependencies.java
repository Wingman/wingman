package com.wingman.client.api.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use this interface to annotate the main plugin class of a plugin that depends several plugins. <br>
 * The value of this annotation is an array of {@link PluginDependency}.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginDependencies {

    PluginDependency[] value();
}
