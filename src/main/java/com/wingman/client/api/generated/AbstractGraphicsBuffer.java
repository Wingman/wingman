package com.wingman.client.api.generated;

import java.awt.Graphics;
import java.awt.Image;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface AbstractGraphicsBuffer {
    void createRasterizer();

    void drawFullGameImage(Graphics arg0, int arg1, int arg2);

    void drawGameImageSection(Graphics arg0, int arg1, int arg2, int arg3, int arg4);

    Image getGraphicsBufferImage();

    @SuppressWarnings("all")
    interface Unsafe {
        void setGraphicsBufferImage(Image value);
    }
}
