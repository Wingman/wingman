package com.wingman.client.api.generated;

import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings("all")
public interface AbstractGraphicsBuffer {
    void drawClippedGameImage(Graphics arg0, int arg1, int arg2, int arg3, int arg4);

    void drawFullGameImage(Graphics arg0, int arg1, int arg2);

    Image getGraphicsBufferImage();

    int[] getPixels();

    int getWidth();

    @SuppressWarnings("all")
    interface Unsafe {
        void setGraphicsBufferImage(Image value);

        void setPixels(int[] value);

        void setWidth(int value);
    }
}
