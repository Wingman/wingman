package com.wingman.client.rs.listeners;

import com.wingman.client.api.events.mouse.MouseWheelMovedCanvasEvent;
import com.wingman.client.plugin.PluginManager;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Created by mmccoy37 on 1/18/17.
 */
public class CanvasMouseWheelListener implements MouseWheelListener {

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        PluginManager.callEvent(new MouseWheelMovedCanvasEvent(mouseWheelEvent));
    }
}
