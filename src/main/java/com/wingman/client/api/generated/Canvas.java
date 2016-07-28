package com.wingman.client.api.generated;

import java.awt.Component;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Canvas {
    Component getGameEngineComponent();

    @SuppressWarnings("all")
    interface Unsafe {
        void setGameEngineComponent(Component value);
    }
}
