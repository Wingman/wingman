package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemContainer extends Node {
    int[] getQuantities();

    int[] getIds();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setQuantities(int[] value);

        void setIds(int[] value);
    }
}
