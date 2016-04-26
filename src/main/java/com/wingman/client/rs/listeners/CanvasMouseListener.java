package com.wingman.client.rs.listeners;

import com.wingman.client.api.events.mouse.*;
import com.wingman.client.plugin.PluginManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CanvasMouseListener implements MouseListener {

    @Override
    public void mouseEntered(MouseEvent e) {
        PluginManager.callEvent(new MouseEnteredCanvasEvent(e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        PluginManager.callEvent(new MouseExitedCanvasEvent(e));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PluginManager.callEvent(new MouseClickedCanvasEvent(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        PluginManager.callEvent(new MousePressedCanvasEvent(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        PluginManager.callEvent(new MouseReleasedCanvasEvent(e));
    }
}
