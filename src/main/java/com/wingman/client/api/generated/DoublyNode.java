package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface DoublyNode extends Node {
    void unlink();

    DoublyNode getNext();

    DoublyNode getPrevious();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setNext(DoublyNode value);

        void setPrevious(DoublyNode value);
    }
}
