package com.wingman.client.api.event;

import java.util.HashSet;
import java.util.Set;

public class EventListenerList {

    public EventListener[] listeners;

    private Set<EventListener> dynamicListeners = new HashSet<>();

    public void register(EventListener eventListener) {
        dynamicListeners.add(eventListener);
    }

    public void bake() {
        if (dynamicListeners != null) {
            listeners = dynamicListeners.toArray(new EventListener[dynamicListeners.size()]);
            dynamicListeners = null;
        }
    }
}
