package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LinkedNode {
    void remove();

    LinkedNode getNext();

    LinkedNode getPrev();

    @SuppressWarnings("all")
    interface Unsafe {
        void setNext(LinkedNode value);

        void setPrev(LinkedNode value);
    }
}
