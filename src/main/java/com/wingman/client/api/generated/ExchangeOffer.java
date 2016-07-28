package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ExchangeOffer {
    byte getStatus();

    int getSpent();

    int getItemId();

    int getTransferred();

    int getItemQuantity();

    int getPrice();

    @SuppressWarnings("all")
    interface Unsafe {
        void setStatus(byte value);

        void setSpent(int value);

        void setItemId(int value);

        void setTransferred(int value);

        void setItemQuantity(int value);

        void setPrice(int value);
    }
}
