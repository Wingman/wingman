package com.wingman.client.api;

import com.wingman.client.classloader.TransformingClassLoader;
import com.wingman.client.rs.GameLoader;

import java.awt.*;

public class ClientBridge {

    /**
     * Gets the client class loader. <br>
     * Can be used for example reflection of members that aren't in the {@link com.wingman.client.api.generated.MappingsDatabase}
     *
     * @return the client class loader
     */
    public static TransformingClassLoader getClassLoader() {
        return (TransformingClassLoader) GameLoader.class.getClassLoader();
    }

    /**
     * Gets the RuneScape canvas from the applet.
     *
     * @return the RuneScape canvas
     */
    public static Canvas getCanvas() {
        if (GameLoader.applet != null) {
            if (GameLoader.applet.getComponentCount() > 0) {
                return (Canvas) GameLoader.applet.getComponent(0);
            }
        }
        return null;
    }
}
