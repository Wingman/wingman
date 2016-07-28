package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Buffer extends Node {
    byte[] getBuffer();

    int getPosition();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setBuffer(byte[] value);

        void setPosition(int value);
    }
}
