package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface DualNodeMap {
    void put(DualNode arg0, long arg1);

    void clear();

    DualNode get(long arg0);

    void remove(long arg0);

    NodeTable getNodeTable();

    DualNodeQueue getDualNodeQueue();

    int getCapacity();

    DualNode getEmptyDualNode();

    int getRemaining();

    @SuppressWarnings("all")
    interface Unsafe {
        void setNodeTable(NodeTable value);

        void setDualNodeQueue(DualNodeQueue value);

        void setCapacity(int value);

        void setEmptyDualNode(DualNode value);

        void setRemaining(int value);
    }
}
