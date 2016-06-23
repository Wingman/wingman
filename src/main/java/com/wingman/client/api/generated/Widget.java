package com.wingman.client.api.generated;

import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Widget extends Node {
    String[] getActions();

    Object[] getConfigListenerArgs();

    int getHeight();

    int getId();

    int getIdx();

    int getInsetX();

    int getInsetY();

    boolean getIsHidden();

    int getItemId();

    int[] getItemIds();

    int getItemQuantity();

    String getName();

    int getQuantity();

    String getText();

    int getWidth();

    int getX();

    int getY();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setActions(String[] value);

        void setConfigListenerArgs(Object[] value);

        void setHeight(int value);

        void setId(int value);

        void setIdx(int value);

        void setInsetX(int value);

        void setInsetY(int value);

        void setIsHidden(boolean value);

        void setItemId(int value);

        void setItemIds(int[] value);

        void setItemQuantity(int value);

        void setName(String value);

        void setQuantity(int value);

        void setText(String value);

        void setWidth(int value);

        void setX(int value);

        void setY(int value);
    }
}
