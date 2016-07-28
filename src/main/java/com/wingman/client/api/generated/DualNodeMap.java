package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface DualNodeMap {
    DualNode get(long arg0);

    void put(DualNode arg0, long arg1);

    void clear();

    void remove(long arg0);

    DualNodeQueue getDualNodeQueue();

    NodeTable getNodeTable();

    int getCapacity();

    DualNode getEmptyDualNode();

    int getRemaining();

    @SuppressWarnings("all")
    interface Unsafe {
        void setDualNodeQueue(DualNodeQueue value);

        void setNodeTable(NodeTable value);

        void setCapacity(int value);

        void setEmptyDualNode(DualNode value);

        void setRemaining(int value);
    }
}
