package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LinkedNodeList {
    Node getNext();

    Node getSentinel();

    @SuppressWarnings("all")
    interface Unsafe {
        void setNext(Node value);

        void setSentinel(Node value);
    }
}
