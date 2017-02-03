package com.wingman.client.api.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this interface to annotate the main plugin class.
 * The main plugin class may be any class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Plugin {

    String id();

    String name();

    String version();

    String description();

    String canToggle() default "false";

    String defaultToggle() default "true";

    /**
     * Use this interface to annotate the setup method.
     * The setup method should perform actions that need to be done before the game is loaded.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Setup {
    }

    /**
     * Use this interface to annotate the activation method.
     * The activation method should initialize all things that don't require cross-plugin interaction.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Activate {
    }

    /**
     * Use this interface to annotate the deactivation method.
     * The deactivation method should cleanup everything.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Deactivate {
    }

    /**
     * The field that has this annotation will get injected an instance of {@link PluginHelper}.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Helper {
    }
}
