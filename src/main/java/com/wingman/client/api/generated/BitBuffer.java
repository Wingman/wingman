package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface BitBuffer extends ByteBuffer {
    int getBits(int arg0);

    IsaacCipher getIsaacCipher();

    int getBitPosition();

    @SuppressWarnings("all")
    interface Unsafe extends ByteBuffer {
        void setIsaacCipher(IsaacCipher value);

        void setBitPosition(int value);
    }
}
