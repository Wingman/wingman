package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Client extends GameEngine {
    void processLogic();

    void processRendering();

    @SuppressWarnings("all")
    interface Unsafe extends GameEngine {
    }
}
