package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Client extends GameEngine {
    void processRendering();

    void processLogic();

    @SuppressWarnings("all")
    interface Unsafe extends GameEngine {
    }
}
