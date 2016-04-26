package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface DualNodeMap {
    int getCapacity();

    DualNodeQueue getDualNodeQueue();

    DualNode getEmptyDualNode();

    NodeTable getNodeTable();

    int getRemaining();

    void clear();

    DualNode get(long arg0);

    void put(DualNode arg0, long arg1);

    void remove(long arg0);

    @SuppressWarnings("all")
    interface Unsafe {
        void setCapacity(int value);

        void setDualNodeQueue(DualNodeQueue value);

        void setEmptyDualNode(DualNode value);

        void setNodeTable(NodeTable value);

        void setRemaining(int value);
    }
}
