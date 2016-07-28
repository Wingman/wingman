package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Friend {
    int getWorld();

    String getName();

    String getPreviousName();

    @SuppressWarnings("all")
    interface Unsafe {
        void setWorld(int value);

        void setName(String value);

        void setPreviousName(String value);
    }
}
