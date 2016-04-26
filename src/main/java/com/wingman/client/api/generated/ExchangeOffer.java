package com.wingman.client.api.generated;

import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ExchangeOffer {
    int getItemId();

    int getItemQuantity();

    int getPrice();

    int getSpent();

    byte getStatus();

    int getTransferred();

    @SuppressWarnings("all")
    interface Unsafe {
        void setItemId(int value);

        void setItemQuantity(int value);

        void setPrice(int value);

        void setSpent(int value);

        void setStatus(byte value);

        void setTransferred(int value);
    }
}
