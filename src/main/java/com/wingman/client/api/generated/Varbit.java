package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Varbit extends DualNode {
    int getLowBit();

    int getHighBit();

    int getVarp();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setLowBit(int value);

        void setHighBit(int value);

        void setVarp(int value);
    }
}
