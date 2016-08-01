package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemDefinition extends DoublyNode {
    int getNoteTemplateIndex();

    int getValue();

    String getName();

    int getNoteIndex();

    String[] getGroundActions();

    int getTeamIndex();

    String[] getWidgetActions();

    @SuppressWarnings("all")
    interface Unsafe extends DoublyNode {
        void setNoteTemplateIndex(int value);

        void setValue(int value);

        void setName(String value);

        void setNoteIndex(int value);

        void setGroundActions(String[] value);

        void setTeamIndex(int value);

        void setWidgetActions(String[] value);
    }
}
