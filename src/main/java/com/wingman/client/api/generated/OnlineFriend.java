package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface OnlineFriend extends LinkedNode {
    String getName();

    short getStatus();

    int getInsertedTime();

    @SuppressWarnings("all")
    interface Unsafe extends LinkedNode {
        void setName(String value);

        void setStatus(short value);

        void setInsertedTime(int value);
    }
}
