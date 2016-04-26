package com.wingman.client.api.events.mouse;

import com.wingman.client.api.event.EventListener;
import com.wingman.client.api.event.EventListenerList;

import java.awt.event.MouseEvent;

public class MouseEnteredCanvasEvent extends MouseCanvasEvent {

    public static final EventListenerList eventListenerList = new EventListenerList();

    public MouseEnteredCanvasEvent(MouseEvent mouseEvent) {
        super(mouseEvent);
    }

    @Override
    public EventListener[] getListeners() {
        return eventListenerList.listeners;
    }
}
