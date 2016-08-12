package com.wingman.client.api.events;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.event.EventListener;
import com.wingman.client.api.event.EventListenerList;
import com.wingman.client.api.generated.NPC;

public class NpcUpdateEvent extends Event {

    public static final EventListenerList eventListenerList = new EventListenerList();

    public final NPC npc;
    public final NpcUpdateEvent.Type type;
    public final int index;

    public NpcUpdateEvent(NPC npc, Type type, int index) {
        this.npc = npc;
        this.type = type;
        this.index = index;
    }

    @Override
    public EventListener[] getListeners() {
        return eventListenerList.listeners;
    }

    public enum Type {
        CREATE,
        UPDATE,
        DELETE
    }
}
