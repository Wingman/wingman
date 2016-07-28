package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NodeTable {
    Node get(long arg0);

    Node first();

    void clear();

    void put(Node arg0, long arg1);

    Node next();

    int getIndex();

    Node[] getBuckets();

    Node getCurrent();

    Node getTail();

    int getSize();

    @SuppressWarnings("all")
    interface Unsafe {
        void setIndex(int value);

        void setBuckets(Node[] value);

        void setCurrent(Node value);

        void setTail(Node value);

        void setSize(int value);
    }
}
