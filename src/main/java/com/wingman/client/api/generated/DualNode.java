package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface DualNode extends Node {
    void deleteDual();

    DualNode getPrevious();

    DualNode getNext();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setPrevious(DualNode value);

        void setNext(DualNode value);
    }
}
