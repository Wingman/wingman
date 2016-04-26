package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NodeDeque {
    Node getHead();

    Node getTail();

    @SuppressWarnings("all")
    interface Unsafe {
        void setHead(Node value);

        void setTail(Node value);
    }
}
