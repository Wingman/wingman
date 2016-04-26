package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface LinkedList {
    LinkedNode getCurrent();

    LinkedNode getHead();

    LinkedNode getBack();

    LinkedNode getPrevious();

    void insertBack(LinkedNode arg0);

    @SuppressWarnings("all")
    interface Unsafe {
        void setCurrent(LinkedNode value);

        void setHead(LinkedNode value);
    }
}
