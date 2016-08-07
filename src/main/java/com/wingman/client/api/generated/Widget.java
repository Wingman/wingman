package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Widget extends Node {
    void rearrangeItemSlot(int arg0, int arg1);

    int[] getItemIds();

    int[] getItemQuantities();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setItemIds(int[] value);

        void setItemQuantities(int[] value);
    }
}
