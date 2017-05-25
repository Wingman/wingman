package com.wingman.client.api.generated;

import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings("all")
public interface GraphicsBuffer extends AbstractGraphicsBuffer {
    void drawClippedGameImage(Graphics arg0, int arg1, int arg2, int arg3, int arg4);

    void drawFullGameImage(Graphics arg0, int arg1, int arg2);

    Image getGraphicsBufferImage();

    @SuppressWarnings("all")
    interface Unsafe extends AbstractGraphicsBuffer {
        void setGraphicsBufferImage(Image value);
    }
}
