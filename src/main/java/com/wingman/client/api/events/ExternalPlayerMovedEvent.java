package com.wingman.client.api.events;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.event.EventListener;
import com.wingman.client.api.event.EventListenerList;

public class ExternalPlayerMovedEvent extends Event {

    public static final EventListenerList eventListenerList = new EventListenerList();

    public final int playerId;
    public final Type type;
    public final int oldX;
    public final int oldY;
    public final int oldPlane;
    public final int newX;
    public final int newY;
    public final int newPlane;

    public ExternalPlayerMovedEvent(int playerId, Type type, int oldX, int oldY, int oldPlane, int newX, int newY, int newPlane) {
        this.playerId = playerId;
        this.type = type;
        this.oldX = oldX;
        this.oldY = oldY;
        this.oldPlane = oldPlane;
        this.newX = newX;
        this.newY = newY;
        this.newPlane = newPlane;
    }

    @Override
    public EventListener[] getListeners() {
        return eventListenerList.listeners;
    }

    public enum Type {
        ADDED_TO_LOCAL,
        PLANE_CHANGE,
        ADJACENT_REGION,
        NONADJACENT_REGION
    }
}
