package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface BitBuffer extends ByteBuffer {
    int getHeader();

    void switchToBitAccess();

    void switchToByteAccess();

    void putHeader(int arg0);

    int readBits(int arg0);

    void setIsaacCipherSeed(int[] arg0);

    int getAvailableBits(int arg0);

    IsaacCipher getIsaacCipher();

    int getBitPosition();

    @SuppressWarnings("all")
    interface Unsafe extends ByteBuffer {
        void setIsaacCipher(IsaacCipher value);

        void setBitPosition(int value);
    }
}
