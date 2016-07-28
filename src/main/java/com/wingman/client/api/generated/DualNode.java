package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface DualNode extends Node {
    void deleteDual();

    DualNode getNext();

    DualNode getPrevious();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setNext(DualNode value);

        void setPrevious(DualNode value);
    }
}
