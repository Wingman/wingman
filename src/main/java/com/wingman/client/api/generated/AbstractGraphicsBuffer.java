package com.wingman.client.api.generated;

import java.awt.Graphics;
import java.awt.Image;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface AbstractGraphicsBuffer {
    void drawClippedGameImage(Graphics arg0, int arg1, int arg2, int arg3, int arg4);

    void drawFullGameImage(Graphics arg0, int arg1, int arg2);

    Image getGraphicsBufferImage();

    @SuppressWarnings("all")
    interface Unsafe {
        void setGraphicsBufferImage(Image value);
    }
}
