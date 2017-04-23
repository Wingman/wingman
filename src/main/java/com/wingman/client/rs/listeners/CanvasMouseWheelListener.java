package com.wingman.client.rs.listeners;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.events.mouse.MouseWheelMovedCanvasEvent;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class CanvasMouseWheelListener implements MouseWheelListener {

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        Event.callEvent(new MouseWheelMovedCanvasEvent(mouseWheelEvent));
    }
}
