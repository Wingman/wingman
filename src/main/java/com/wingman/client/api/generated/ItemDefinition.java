package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemDefinition extends DoublyNode {
    int getNoteIndex();

    int getTeamIndex();

    String[] getGroundActions();

    String getName();

    int getNoteTemplateIndex();

    int getValue();

    String[] getWidgetActions();

    @SuppressWarnings("all")
    interface Unsafe extends DoublyNode {
        void setNoteIndex(int value);

        void setTeamIndex(int value);

        void setGroundActions(String[] value);

        void setName(String value);

        void setNoteTemplateIndex(int value);

        void setValue(int value);

        void setWidgetActions(String[] value);
    }
}
