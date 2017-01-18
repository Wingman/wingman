package com.wingman.client.api.events.mouse;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.event.EventListener;
import com.wingman.client.api.event.EventListenerList;

import java.awt.event.MouseWheelEvent;

public class MouseWheelMovedCanvasEvent extends Event {

    public static final EventListenerList eventListenerList = new EventListenerList();

    MouseWheelEvent mouseWheelEvent;
    public MouseWheelMovedCanvasEvent(MouseWheelEvent mouseWheelEvent) {
        this.mouseWheelEvent = mouseWheelEvent;
    }

    public EventListener[] getListeners() {
        return eventListenerList.listeners;
    }

}
