package com.wingman.client.api;

import com.wingman.client.classloader.TransformingClassLoader;
import com.wingman.client.rs.GameLoader;
import com.wingman.client.ui.Client;

import java.awt.*;

public class ClientBridge {

    /**
     * Attempts to display a notification in the tray using the {@link TrayIcon} API <br>
     * if notifications are enabled (see {@link com.wingman.client.Settings}).
     *
     * @param caption the caption displayed above the text, usually in bold; may be {@code null}
     * @param text the text displayed for the particular message; may be {@code null}
     * @param messageType an enum indicating the message type
     * @throws NullPointerException if both <code>caption</code>
     */
    public static void displayTrayNotification(String caption, String text, TrayIcon.MessageType messageType) {
        if (Client.clientTrayIcon != null) {
            Client.clientTrayIcon.icon.displayMessage(caption, text, messageType);
        }
    }

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
