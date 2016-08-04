package com.wingman.client.api.generated;

import java.awt.Graphics;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface PrimaryGraphicsBuffer extends AbstractGraphicsBuffer {
    void drawFullGameImage(Graphics arg0, int arg1, int arg2);

    void drawClippedGameImage(Graphics arg0, int arg1, int arg2, int arg3, int arg4);

    @SuppressWarnings("all")
    interface Unsafe extends AbstractGraphicsBuffer {
    }
}
