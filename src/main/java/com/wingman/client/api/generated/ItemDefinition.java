package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemDefinition extends DualNode {
    boolean getIsMembers();

    String[] getWidgetOptions();

    String getName();

    int getPrice();

    int getTeam();

    int getNotedId();

    int getUnNotedId();

    int getContrast();

    int getAmbient();

    String[] getOptions();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setIsMembers(boolean value);

        void setWidgetOptions(String[] value);

        void setName(String value);

        void setPrice(int value);

        void setTeam(int value);

        void setNotedId(int value);

        void setUnNotedId(int value);

        void setContrast(int value);

        void setAmbient(int value);

        void setOptions(String[] value);
    }
}
