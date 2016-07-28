package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface GroundItem extends Entity {
    Model getModel();

    int getQuantity();

    int getId();

    @SuppressWarnings("all")
    interface Unsafe extends Entity {
        void setQuantity(int value);

        void setId(int value);
    }
}
