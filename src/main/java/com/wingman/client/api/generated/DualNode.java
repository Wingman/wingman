package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface DualNode extends Node {
    DualNode getNext();

    DualNode getPrevious();

    void deleteDual();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setNext(DualNode value);

        void setPrevious(DualNode value);
    }
}
