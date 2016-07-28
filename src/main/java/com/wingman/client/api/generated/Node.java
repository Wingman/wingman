package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Node {
    void delete();

    boolean isLinked();

    Node getNext();

    long getKey();

    Node getPrevious();

    @SuppressWarnings("all")
    interface Unsafe {
        void setNext(Node value);

        void setKey(long value);

        void setPrevious(Node value);
    }
}
