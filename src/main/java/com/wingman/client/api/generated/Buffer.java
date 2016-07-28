package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Buffer extends Node {
    int getPosition();

    byte[] getBuffer();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setPosition(int value);

        void setBuffer(byte[] value);
    }
}
