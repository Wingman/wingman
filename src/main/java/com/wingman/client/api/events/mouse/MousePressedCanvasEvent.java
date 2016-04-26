package com.wingman.client.api.events.mouse;

import com.wingman.client.api.event.EventListener;
import com.wingman.client.api.event.EventListenerList;

import java.awt.event.MouseEvent;

public class MousePressedCanvasEvent extends MouseCanvasEvent {

    public static final EventListenerList eventListenerList = new EventListenerList();

    public MousePressedCanvasEvent(MouseEvent mouseEvent) {
        super(mouseEvent);
    }

    @Override
    public EventListener[] getListeners() {
        return eventListenerList.listeners;
    }
}
