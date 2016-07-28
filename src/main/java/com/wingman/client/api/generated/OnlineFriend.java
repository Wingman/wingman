package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface OnlineFriend extends LinkedNode {
    int getInsertedTime();

    short getStatus();

    String getName();

    @SuppressWarnings("all")
    interface Unsafe extends LinkedNode {
        void setInsertedTime(int value);

        void setStatus(short value);

        void setName(String value);
    }
}
