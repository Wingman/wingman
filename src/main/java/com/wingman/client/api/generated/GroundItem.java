package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface GroundItem extends Entity {
    int getId();

    int getQuantity();

    Model getModel();

    @SuppressWarnings("all")
    interface Unsafe extends Entity {
        void setId(int value);

        void setQuantity(int value);
    }
}
