package com.wingman.client.api.enums;

public final class GameState {

    public static final int TITLE = 10;
    public static final int LOGIN = 20;
    public static final int LOADING = 25;
    public static final int PLAYING = 30;
    public static final int DISCONNECTED = 40;
    public static final int QUICKHOPPING = 45;

    private GameState() {
        // This class should not be instantiated
    }
}
