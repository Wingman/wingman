package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface DualNodeQueue {
    void clear();

    DualNode pop();

    void add(DualNode arg0);

    @SuppressWarnings("all")
    interface Unsafe {
    }
}
