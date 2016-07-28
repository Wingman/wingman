package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Node {
    boolean isLinked();

    void delete();

    Node getPrevious();

    long getKey();

    Node getNext();

    @SuppressWarnings("all")
    interface Unsafe {
        void setPrevious(Node value);

        void setKey(long value);

        void setNext(Node value);
    }
}
