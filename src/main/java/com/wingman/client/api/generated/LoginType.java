package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LoginType {
    int getLoginId();

    int getId();

    int getType();

    @SuppressWarnings("all")
    interface Unsafe {
        void setId(int value);

        void setType(int value);
    }
}
