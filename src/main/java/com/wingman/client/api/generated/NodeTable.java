package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface NodeTable {
    Node[] getBuckets();

    Node getCurrent();

    int getIndex();

    int getSize();

    Node getTail();

    void clear();

    Node first();

    Node get(long arg0);

    Node next();

    void put(Node arg0, long arg1);

    @SuppressWarnings("all")
    interface Unsafe {
        void setBuckets(Node[] value);

        void setCurrent(Node value);

        void setIndex(int value);

        void setSize(int value);

        void setTail(Node value);
    }
}
