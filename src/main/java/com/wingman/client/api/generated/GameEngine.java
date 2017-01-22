package com.wingman.client.api.generated;

import java.awt.event.FocusListener;
import java.awt.event.WindowListener;

@SuppressWarnings("all")
public interface GameEngine extends FocusListener, WindowListener, Runnable {
    void throwCriticalError(String errorMessage);

    @SuppressWarnings("all")
    interface Unsafe extends FocusListener, WindowListener, Runnable {
    }
}
