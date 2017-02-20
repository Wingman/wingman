package com.wingman.client.rs.listeners;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.events.mouse.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CanvasMouseListener implements MouseListener {

    @Override
    public void mouseEntered(MouseEvent e) {
        Event.callEvent(new MouseEnteredCanvasEvent(e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Event.callEvent(new MouseExitedCanvasEvent(e));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Event.callEvent(new MouseClickedCanvasEvent(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Event.callEvent(new MousePressedCanvasEvent(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Event.callEvent(new MouseReleasedCanvasEvent(e));
    }
}
