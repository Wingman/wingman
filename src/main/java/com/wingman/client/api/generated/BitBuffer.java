package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface BitBuffer extends ByteBuffer {
    int getBits(int arg0);

    int getBitPosition();

    IsaacCipher getIsaacCipher();

    @SuppressWarnings("all")
    interface Unsafe extends ByteBuffer {
        void setBitPosition(int value);

        void setIsaacCipher(IsaacCipher value);
    }
}
