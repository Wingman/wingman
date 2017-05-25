package com.wingman.client.api.generated;

@SuppressWarnings("all")
public interface AbstractGraphicsBuffer {
    int getHeight();

    int[] getPixels();

    int getWidth();

    @SuppressWarnings("all")
    interface Unsafe {
        void setHeight(int value);

        void setPixels(int[] value);

        void setWidth(int value);
    }
}
