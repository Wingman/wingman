package com.wingman.client.api.generated;

import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface Widget extends Node {
    int getX();

    int getInsetY();

    String getText();

    int getItemQuantity();

    int getInsetX();

    int[] getItemIds();

    Object[] getConfigListenerArgs();

    int getHeight();

    int getY();

    String[] getActions();

    String getName();

    int getWidth();

    int getIdx();

    boolean getIsHidden();

    int getItemId();

    @SuppressWarnings("all")
    interface Unsafe extends Node {
        void setX(int value);

        void setInsetY(int value);

        void setText(String value);

        void setItemQuantity(int value);

        void setInsetX(int value);

        void setItemIds(int[] value);

        void setConfigListenerArgs(Object[] value);

        void setHeight(int value);

        void setY(int value);

        void setActions(String[] value);

        void setName(String value);

        void setWidth(int value);

        void setIdx(int value);

        void setIsHidden(boolean value);

        void setItemId(int value);
    }
}
