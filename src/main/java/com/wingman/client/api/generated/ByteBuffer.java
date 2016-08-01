package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ByteBuffer extends Node {
    int getPosition();

    byte[] getPayload();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setPosition(int value);

        void setPayload(byte[] value);
    }
}
