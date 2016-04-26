package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LoginType {
    int getId();

    int getType();

    int getLoginId();

    @SuppressWarnings("all")
    interface Unsafe {
        void setId(int value);

        void setType(int value);
    }
}
