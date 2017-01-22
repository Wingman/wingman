package com.wingman.client.api.generated;

@SuppressWarnings("all")
public interface ItemContainer extends Node {
    int[] getIds();

    int[] getQuantities();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setIds(int[] value);

        void setQuantities(int[] value);
    }
}
