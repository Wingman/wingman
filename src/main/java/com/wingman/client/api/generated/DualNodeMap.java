package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface DualNodeMap {
    void clear();

    DualNode get(long arg0);

    void put(DualNode arg0, long arg1);

    void remove(long arg0);

    int getRemaining();

    DualNodeQueue getDualNodeQueue();

    int getCapacity();

    NodeTable getNodeTable();

    DualNode getEmptyDualNode();

    @SuppressWarnings("all")
    interface Unsafe {
        void setRemaining(int value);

        void setDualNodeQueue(DualNodeQueue value);

        void setCapacity(int value);

        void setNodeTable(NodeTable value);

        void setEmptyDualNode(DualNode value);
    }
}
