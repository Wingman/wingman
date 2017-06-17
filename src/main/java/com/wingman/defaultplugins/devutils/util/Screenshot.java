package com.wingman.defaultplugins.devutils.util;

import com.wingman.client.ui.Client;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Screenshot {

    /**
     * Returns a screen shot of the current state of the actual client.
     *
     * @return a {@link BufferedImage} displaying the whole client frame, including the game with overlays
     * @throws AWTException if the client user's platform
     *         does not support low-level input control
     */
    public static BufferedImage getClientScreenShot() throws AWTException {
        Robot robot = new Robot();
        Rectangle bounds = Client.frame.getBounds();
        return robot.createScreenCapture(bounds);
    }
}
