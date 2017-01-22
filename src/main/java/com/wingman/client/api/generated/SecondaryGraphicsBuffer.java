package com.wingman.client.api.generated;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

@SuppressWarnings("all")
public interface SecondaryGraphicsBuffer extends AbstractGraphicsBuffer, ImageObserver, ImageProducer {
    void drawClippedGameImage(Graphics arg0, int arg1, int arg2, int arg3, int arg4);

    void drawFullGameImage(Graphics arg0, int arg1, int arg2);

    @SuppressWarnings("all")
    interface Unsafe extends AbstractGraphicsBuffer, ImageObserver, ImageProducer {
    }
}
