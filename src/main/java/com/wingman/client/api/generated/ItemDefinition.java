package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemDefinition extends DualNode {
    int getAmbient();

    int getContrast();

    boolean getIsMembers();

    String getName();

    int getNotedId();

    String[] getOptions();

    int getPrice();

    int getTeam();

    int getUnNotedId();

    String[] getWidgetOptions();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setAmbient(int value);

        void setContrast(int value);

        void setIsMembers(boolean value);

        void setName(String value);

        void setNotedId(int value);

        void setOptions(String[] value);

        void setPrice(int value);

        void setTeam(int value);

        void setUnNotedId(int value);

        void setWidgetOptions(String[] value);
    }
}
