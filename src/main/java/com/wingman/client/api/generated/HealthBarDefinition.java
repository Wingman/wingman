package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface HealthBarDefinition extends DualNode {
    int getWidth();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setWidth(int value);
    }
}
