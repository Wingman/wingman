package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface GameEngine {
    boolean getErrorHasBeenThrown();

    void load(int arg0, int arg1, int arg2);

    void processGameLoop();

    void processLogic(int arg0);

    void processRendering(int arg0);

    void throwCriticalError(String arg0);

    @SuppressWarnings("all")
    interface Unsafe {
        void setErrorHasBeenThrown(boolean value);
    }
}
