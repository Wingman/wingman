package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Entity extends DualNode {
    Model getModel();

    void renderAtPoint(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8);

    int getModelHeight();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setModelHeight(int value);
    }
}
