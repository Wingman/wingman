package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Friend {
    String getName();

    String getPreviousName();

    int getWorld();

    @SuppressWarnings("all")
    interface Unsafe {
        void setName(String value);

        void setPreviousName(String value);

        void setWorld(int value);
    }
}
