package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NodeTable {
    void put(Node arg0, long arg1);

    Node first();

    Node get(long arg0);

    void clear();

    Node next();

    int getIndex();

    Node getTail();

    Node[] getBuckets();

    int getSize();

    Node getCurrent();

    @SuppressWarnings("all")
    interface Unsafe {
        void setIndex(int value);

        void setTail(Node value);

        void setBuckets(Node[] value);

        void setSize(int value);

        void setCurrent(Node value);
    }
}
