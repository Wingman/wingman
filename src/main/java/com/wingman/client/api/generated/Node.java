package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Node {
    boolean isLinked();

    void unlink();

    long getKey();

    Node getNext();

    Node getPrevious();

    @SuppressWarnings("all")
    interface Unsafe {
        void setKey(long value);

        void setNext(Node value);

        void setPrevious(Node value);
    }
}
