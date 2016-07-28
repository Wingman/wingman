package com.wingman.client.api.generated;

import java.lang.Object;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface RuneScript extends Node {
    Object[] getArgs();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setArgs(Object[] value);
    }
}
