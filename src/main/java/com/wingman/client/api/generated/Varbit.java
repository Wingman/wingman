package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Varbit extends DualNode {
    int getHighBit();

    int getLowBit();

    int getVarp();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setHighBit(int value);

        void setLowBit(int value);

        void setVarp(int value);
    }
}
