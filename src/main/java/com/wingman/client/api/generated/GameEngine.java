package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface GameEngine {
    void processLogic();

    void throwCriticalError(String arg0);

    void processGameLoop();

    void processRendering();

    void load(int arg0, int arg1, int arg2);

    boolean getErrorHasBeenThrown();

    @SuppressWarnings("all")
    interface Unsafe {
        void setErrorHasBeenThrown(boolean value);
    }
}
