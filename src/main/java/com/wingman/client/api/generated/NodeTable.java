package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NodeTable {
    Node next();

    void put(Node arg0, long arg1);

    Node get(long arg0);

    Node first();

    void clear();

    Node[] getBuckets();

    Node getCurrent();

    int getSize();

    Node getTail();

    int getIndex();

    @SuppressWarnings("all")
    interface Unsafe {
        void setBuckets(Node[] value);

        void setCurrent(Node value);

        void setSize(int value);

        void setTail(Node value);

        void setIndex(int value);
    }
}
