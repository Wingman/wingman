package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ExchangeOffer {
    int getSpent();

    int getTransferred();

    int getPrice();

    int getItemId();

    byte getStatus();

    int getItemQuantity();

    @SuppressWarnings("all")
    interface Unsafe {
        void setSpent(int value);

        void setTransferred(int value);

        void setPrice(int value);

        void setItemId(int value);

        void setStatus(byte value);

        void setItemQuantity(int value);
    }
}
