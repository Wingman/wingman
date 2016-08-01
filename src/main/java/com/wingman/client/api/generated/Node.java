package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Node {
    boolean isLinked();

    void unlink();

    Node getPrevious();

    Node getNext();

    long getKey();

    @SuppressWarnings("all")
    interface Unsafe {
        void setPrevious(Node value);

        void setNext(Node value);

        void setKey(long value);
    }
}
