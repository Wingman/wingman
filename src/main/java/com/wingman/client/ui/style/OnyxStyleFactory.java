package com.wingman.client.ui.style;

import java.awt.*;

public class OnyxStyleFactory {

    public static final Color BASE = Color.decode("#3a3a3a");
    public static final Color BASE_DARKER = BASE.darker();
    public static final Color BASE_BRIGHTER = BASE.brighter();
    public static final Color BASE_BRIGHTEST = BASE_BRIGHTER.brighter();

    public static final Color BASE_BLUE = Color.decode("#22A7F0");
    public static final Color BASE_BLUE_DARKER = BASE_BLUE.darker();

    public static final Color PRIMARY_TEXT_COLOR = Color.decode("#F5F5F5");
    public static final Color SECONDARY_TEXT_COLOR = Color.decode("#E0E0E0");

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    public static final Font ROBOTO_REGULAR = new Font("Roboto", Font.PLAIN, 12);
    public static final Font ROBOTO_MEDIUM = new Font("Roboto Medium", Font.PLAIN, 12);
}
