package com.wingman.client.api.generated;

import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Widget extends Node {
    int getItemId();

    int getWidth();

    boolean getIsHidden();

    Object[] getConfigListenerArgs();

    int getY();

    String[] getActions();

    String getName();

    int getX();

    int getHeight();

    int getItemQuantity();

    int getIdx();

    int getInsetX();

    int getInsetY();

    String getText();

    int[] getItemIds();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setItemId(int value);

        void setWidth(int value);

        void setIsHidden(boolean value);

        void setConfigListenerArgs(Object[] value);

        void setY(int value);

        void setActions(String[] value);

        void setName(String value);

        void setX(int value);

        void setHeight(int value);

        void setItemQuantity(int value);

        void setIdx(int value);

        void setInsetX(int value);

        void setInsetY(int value);

        void setText(String value);

        void setItemIds(int[] value);
    }
}
