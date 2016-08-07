package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface MessageContainer {
    Message addMessage(int type, String prefix, String message, String suffix);

    @SuppressWarnings("all")
    interface Unsafe {
    }
}
