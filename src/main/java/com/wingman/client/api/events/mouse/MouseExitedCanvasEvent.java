package com.wingman.client.api.events.mouse;

import com.wingman.client.api.event.AbstractEventListener;
import com.wingman.client.api.event.EventListenerList;

import java.awt.event.MouseEvent;

public class MouseExitedCanvasEvent extends AbstractMouseCanvasEvent {

    public static final EventListenerList eventListenerList = new EventListenerList();

    public MouseExitedCanvasEvent(MouseEvent mouseEvent) {
        super(mouseEvent);
    }

    @Override
    public AbstractEventListener[] getListeners() {
        return eventListenerList.listeners;
    }
}
