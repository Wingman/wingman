package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LinkedList {
    void insertBack(LinkedNode arg0);

    LinkedNode getBack();

    LinkedNode getPrevious();

    LinkedNode getCurrent();

    LinkedNode getHead();

    @SuppressWarnings("all")
    interface Unsafe {
        void setCurrent(LinkedNode value);

        void setHead(LinkedNode value);
    }
}
