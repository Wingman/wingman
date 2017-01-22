package com.wingman.client.api.generated;

@SuppressWarnings("all")
public interface Item extends Entity {
    Model getModel();

    int getId();

    int getQuantity();

    @SuppressWarnings("all")
    interface Unsafe extends Entity {
        void setId(int value);

        void setQuantity(int value);
    }
}
