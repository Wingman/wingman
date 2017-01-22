package com.wingman.client.api.generated;

@SuppressWarnings("all")
public interface MessageContainer {
    Message addMessage(int type, String prefix, String message, String suffix);

    @SuppressWarnings("all")
    interface Unsafe {
    }
}
