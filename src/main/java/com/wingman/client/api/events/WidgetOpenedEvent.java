package com.wingman.client.api.events;

import com.wingman.client.api.event.AbstractEventListener;
import com.wingman.client.api.event.Event;
import com.wingman.client.api.event.EventListenerList;

public class WidgetOpenedEvent extends Event {

    public static final EventListenerList eventListenerList = new EventListenerList();

    public int widgetId;

    public WidgetOpenedEvent(int widgetId) {
        this.widgetId = widgetId;
    }

    @Override
    public AbstractEventListener[] getListeners() {
        return eventListenerList.listeners;
    }
}
