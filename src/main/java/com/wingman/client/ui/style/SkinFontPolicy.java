package com.wingman.client.ui.style;

import org.pushingpixels.substance.api.fonts.FontPolicy;
import org.pushingpixels.substance.api.fonts.FontSet;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class SkinFontPolicy implements FontPolicy {

    FontUIResource generalFont = new FontUIResource(new Font("Helvetica", Font.PLAIN, 12));

    @Override
    public FontSet getFontSet(String lafName, UIDefaults table) {
        return new FontSet() {
            @Override
            public FontUIResource getControlFont() {
                return generalFont;
            }

            @Override
            public FontUIResource getMenuFont() {
                return generalFont;
            }

            @Override
            public FontUIResource getTitleFont() {
                return generalFont;
            }

            @Override
            public FontUIResource getWindowTitleFont() {
                return generalFont;
            }

            @Override
            public FontUIResource getSmallFont() {
                return generalFont;
            }

            @Override
            public FontUIResource getMessageFont() {
                return generalFont;
            }
        };
    }
}
