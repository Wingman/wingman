package com.wingman.client.api.generated;

import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Widget extends Node {
    int getX();

    String getName();

    boolean getIsHidden();

    String[] getActions();

    int getY();

    int getHeight();

    int getInsetX();

    int getInsetY();

    int getIdx();

    int getWidth();

    int getItemQuantity();

    int[] getItemIds();

    int getItemId();

    Object[] getConfigListenerArgs();

    String getText();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setX(int value);

        void setName(String value);

        void setIsHidden(boolean value);

        void setActions(String[] value);

        void setY(int value);

        void setHeight(int value);

        void setInsetX(int value);

        void setInsetY(int value);

        void setIdx(int value);

        void setWidth(int value);

        void setItemQuantity(int value);

        void setItemIds(int[] value);

        void setItemId(int value);

        void setConfigListenerArgs(Object[] value);

        void setText(String value);
    }
}
