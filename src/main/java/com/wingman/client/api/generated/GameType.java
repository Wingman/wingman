package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface GameType {
    int getGameId();

    int getId();

    String getGameName();

    @SuppressWarnings("all")
    interface Unsafe {
        void setId(int value);

        void setGameName(String value);
    }
}
