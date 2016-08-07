package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Widget extends Node {
    void rearrangeItemSlot(int targetSlot, int sourceSlot);

    int[] getItemIds();

    int[] getItemQuantities();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setItemIds(int[] value);

        void setItemQuantities(int[] value);
    }
}
