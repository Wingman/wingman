package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ExchangeOffer {
    int getPrice();

    byte getStatus();

    int getItemQuantity();

    int getSpent();

    int getTransferred();

    int getItemId();

    @SuppressWarnings("all")
    interface Unsafe {
        void setPrice(int value);

        void setStatus(byte value);

        void setItemQuantity(int value);

        void setSpent(int value);

        void setTransferred(int value);

        void setItemId(int value);
    }
}
