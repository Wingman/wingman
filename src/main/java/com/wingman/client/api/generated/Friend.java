package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Friend {
    String getPreviousName();

    int getWorld();

    String getName();

    @SuppressWarnings("all")
    interface Unsafe {
        void setPreviousName(String value);

        void setWorld(int value);

        void setName(String value);
    }
}
