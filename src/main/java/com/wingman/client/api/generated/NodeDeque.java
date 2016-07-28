package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NodeDeque {
    Node getTail();

    Node getHead();

    @SuppressWarnings("all")
    interface Unsafe {
        void setTail(Node value);

        void setHead(Node value);
    }
}
