package com.wingman.client.api.generated;

import java.awt.Graphics;
import java.awt.Image;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface AbstractGraphicsBuffer {
    Image getGraphicsBufferImage();

    void createRasterizer();

    void drawGameGraphics(Graphics arg0, int arg1, int arg2, int arg3, int arg4, byte arg5);

    void drawTitleGraphics(Graphics arg0, int arg1, int arg2, int arg3);

    @SuppressWarnings("all")
    interface Unsafe {
        void setGraphicsBufferImage(Image value);
    }
}
