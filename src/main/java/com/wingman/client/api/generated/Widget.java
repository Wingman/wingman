package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Widget extends Node {
    void rearrangeItemSlot(int targetSlot, int sourceSlot);

    int[] getItemIds();

    int[] getItemQuantities();

    String getName();

    Widget getParent();

    String getText();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setItemIds(int[] value);

        void setItemQuantities(int[] value);

        void setName(String value);

        void setParent(Widget value);

        void setText(String value);
    }
}
