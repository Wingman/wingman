package com.wingman.client.api.generated;

import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings("all")
public interface ItemDefinition extends DualNode {
    String[] getGroundActions();

    String getName();

    int getNoteIndex();

    int getNoteTemplateIndex();

    int getTeamIndex();

    int getValue();

    String[] getWidgetActions();

    @SuppressWarnings("all")
    interface Unsafe extends DualNode {
        void setGroundActions(String[] value);

        void setName(String value);

        void setNoteIndex(int value);

        void setNoteTemplateIndex(int value);

        void setTeamIndex(int value);

        void setValue(int value);

        void setWidgetActions(String[] value);
    }
}
