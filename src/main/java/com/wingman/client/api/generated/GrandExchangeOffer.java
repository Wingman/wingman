package com.wingman.client.api.generated;

@SuppressWarnings("all")
public interface GrandExchangeOffer {
    int getCompletedPrice();

    int getCompletedQuantity();

    int getItemId();

    int getPrice();

    int getQuantity();

    byte getState();

    @SuppressWarnings("all")
    interface Unsafe {
        void setCompletedPrice(int value);

        void setCompletedQuantity(int value);

        void setItemId(int value);

        void setPrice(int value);

        void setQuantity(int value);

        void setState(byte value);
    }
}
