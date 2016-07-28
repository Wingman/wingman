package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface OnlineFriend extends LinkedNode {
    short getStatus();

    int getInsertedTime();

    String getName();

    @SuppressWarnings("all")
    interface Unsafe extends LinkedNode {
        void setStatus(short value);

        void setInsertedTime(int value);

        void setName(String value);
    }
}
