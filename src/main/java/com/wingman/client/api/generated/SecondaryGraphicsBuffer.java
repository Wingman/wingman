package com.wingman.client.api.generated;

import java.awt.Graphics;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface SecondaryGraphicsBuffer extends AbstractGraphicsBuffer {
    void drawGameGraphics(Graphics arg0, int arg1, int arg2, int arg3, int arg4);

    void drawTitleGraphics(Graphics arg0, int arg1, int arg2);

    @SuppressWarnings("all")
    interface Unsafe extends AbstractGraphicsBuffer {
    }
}
