package com.wingman.client.api.event;

import java.util.HashSet;
import java.util.Set;

public class EventListenerList {

    public AbstractEventListener[] listeners;

    private Set<AbstractEventListener> dynamicListeners = new HashSet<>();

    public void register(AbstractEventListener eventListener) {
        dynamicListeners.add(eventListener);
    }

    public void bake() {
        if (dynamicListeners != null) {
            listeners = dynamicListeners.toArray(new AbstractEventListener[dynamicListeners.size()]);
            dynamicListeners = null;
        }
    }
}
