package com.wingman.client.api.events;

import com.wingman.client.api.event.AbstractEventListener;
import com.wingman.client.api.event.Event;
import com.wingman.client.api.event.EventListenerList;
import com.wingman.client.api.generated.ItemDefinition;

public class ItemDefinitionCachedEvent extends Event {

    public static final EventListenerList eventListenerList = new EventListenerList();

    public ItemDefinition itemDefinition;

    public ItemDefinitionCachedEvent(ItemDefinition itemDefinition) {
        this.itemDefinition = itemDefinition;
    }

    @Override
    public AbstractEventListener[] getListeners() {
        return eventListenerList.listeners;
    }
}
