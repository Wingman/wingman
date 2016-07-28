package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;
import java.nio.ByteBuffer;

@SuppressWarnings("all")
public interface SimpleByteBuffer extends AbstractByteBuffer {
    void set(byte[] arg0);

    byte[] getAsBytes();

    ByteBuffer getByteBuffer();

    @SuppressWarnings("all")
    interface Unsafe extends AbstractByteBuffer {
        void setByteBuffer(ByteBuffer value);
    }
}
