package com.wingman.client.api.event;

public abstract class Event {

    public abstract AbstractEventListener[] getListeners();

    /**
     * Propagates an {@link Event} to its event listeners.
     *
     * @param event the event object that should be propagated to its listeners
     */
    public static void callEvent(Event event) {
        AbstractEventListener[] listeners = event.getListeners();
        if (listeners != null) {
            for (AbstractEventListener eventListener : listeners) {
                if (eventListener != null) {
                    eventListener.runEvent(event);
                }
            }
        }
    }
}
