package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Node {
    void delete();

    boolean isLinked();

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
