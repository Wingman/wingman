package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LinkedNode {
    LinkedNode getNext();

    LinkedNode getPrev();

    void remove();

    @SuppressWarnings("all")
    interface Unsafe {
        void setNext(LinkedNode value);

        void setPrev(LinkedNode value);
    }
}
