package com.wingman.client.api.events.mouse;

import com.wingman.client.api.event.EventListener;
import com.wingman.client.api.event.EventListenerList;

import java.awt.event.MouseEvent;

public class MouseReleasedCanvasEvent extends MouseCanvasEvent {

    public static final EventListenerList eventListenerList = new EventListenerList();

    public MouseReleasedCanvasEvent(MouseEvent mouseEvent) {
        super(mouseEvent);
    }

    @Override
    public EventListener[] getListeners() {
        return eventListenerList.listeners;
    }
}
