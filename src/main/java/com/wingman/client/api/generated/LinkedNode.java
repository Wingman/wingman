package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LinkedNode {
    void remove();

    LinkedNode getPrev();

    LinkedNode getNext();

    @SuppressWarnings("all")
    interface Unsafe {
        void setPrev(LinkedNode value);

        void setNext(LinkedNode value);
    }
}
