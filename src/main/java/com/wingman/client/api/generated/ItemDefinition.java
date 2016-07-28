package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemDefinition extends DualNode {
    String[] getOptions();

    int getUnNotedId();

    int getAmbient();

    int getPrice();

    int getContrast();

    int getTeam();

    boolean getIsMembers();

    String[] getWidgetOptions();

    String getName();

    int getNotedId();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setOptions(String[] value);

        void setUnNotedId(int value);

        void setAmbient(int value);

        void setPrice(int value);

        void setContrast(int value);

        void setTeam(int value);

        void setIsMembers(boolean value);

        void setWidgetOptions(String[] value);

        void setName(String value);

        void setNotedId(int value);
    }
}
