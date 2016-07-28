package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface DualNodeQueue {
    void add(DualNode arg0);

    void clear();

    DualNode pop();

    @SuppressWarnings("all")
    interface Unsafe {
    }
}
