package com.wingman.client.api.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this interface to annotate the main plugin class. <br>
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
     * Use this interface to annotate the setup method. <br>
     * The setup method should perform actions that need to be done before the game is loaded. <br>
     * This method must be static.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Setup {
    }

    /**
     * Use this interface to annotate the activation method. <br>
     * The activation method should initialize all things that don't require cross-plugin interaction. <br>
     * This method must be static.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Activate {
    }

    /**
     * Use this interface to annotate the deactivation method. <br>
     * The deactivation method should cleanup everything. <br>
     * This method must be static. <br>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Deactivate {
    }

    /**
     * The field that has this annotation will get injected an instance of {@link PluginHelper}. <br>
     * This field must be static.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Helper {
    }
}
