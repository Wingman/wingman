package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface GameType {
    int getGameId();

    String getGameName();

    int getId();

    @SuppressWarnings("all")
    interface Unsafe {
        void setGameName(String value);

        void setId(int value);
    }
}
