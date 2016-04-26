package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface OnlineFriend extends LinkedNode {
    int getInsertedTime();

    String getName();

    short getStatus();

    @SuppressWarnings("all")
    interface Unsafe extends LinkedNode {
        void setInsertedTime(int value);

        void setName(String value);

        void setStatus(short value);
    }
}
