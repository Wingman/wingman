package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemDefinition extends DualNode {
    String[] getOptions();

    boolean getIsMembers();

    int getUnNotedId();

    int getAmbient();

    int getPrice();

    int getNotedId();

    String[] getWidgetOptions();

    int getContrast();

    int getTeam();

    String getName();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setOptions(String[] value);

        void setIsMembers(boolean value);

        void setUnNotedId(int value);

        void setAmbient(int value);

        void setPrice(int value);

        void setNotedId(int value);

        void setWidgetOptions(String[] value);

        void setContrast(int value);

        void setTeam(int value);

        void setName(String value);
    }
}
