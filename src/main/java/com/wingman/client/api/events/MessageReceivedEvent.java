package com.wingman.client.api.events;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.event.EventListener;
import com.wingman.client.api.event.EventListenerList;
import com.wingman.client.api.generated.Message;

public class MessageReceivedEvent extends Event {

    public static final EventListenerList eventListenerList = new EventListenerList();

    public Message message;

    public MessageReceivedEvent(Message message) {
        this.message = message;
    }

    @Override
    public EventListener[] getListeners() {
        return eventListenerList.listeners;
    }
}
