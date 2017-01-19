package com.wingman.client.rs.listeners;

import com.wingman.client.api.events.mouse.MouseWheelMovedCanvasEvent;
import com.wingman.client.plugin.PluginManager;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class CanvasMouseWheelListener implements MouseWheelListener {

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        PluginManager.callEvent(new MouseWheelMovedCanvasEvent(mouseWheelEvent));
    }
}
