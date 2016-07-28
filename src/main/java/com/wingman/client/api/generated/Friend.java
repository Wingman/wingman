package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Friend {
    String getName();

    int getWorld();

    String getPreviousName();

    @SuppressWarnings("all")
    interface Unsafe {
        void setName(String value);

        void setWorld(int value);

        void setPreviousName(String value);
    }
}
