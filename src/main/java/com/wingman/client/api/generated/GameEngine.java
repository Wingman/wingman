package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface GameEngine {
    void throwCriticalError(String arg0);

    void processLogic();

    void processRendering();

    void load(int arg0, int arg1, int arg2);

    void processGameLoop();

    boolean getErrorHasBeenThrown();

    @SuppressWarnings("all")
    interface Unsafe {
        void setErrorHasBeenThrown(boolean value);
    }
}
