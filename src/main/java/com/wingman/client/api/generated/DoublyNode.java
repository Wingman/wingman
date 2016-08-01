package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface DoublyNode extends Node {
    void unlink();

    DoublyNode getPrevious();

    DoublyNode getNext();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setPrevious(DoublyNode value);

        void setNext(DoublyNode value);
    }
}
