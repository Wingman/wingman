package com.wingman.client.api.events;

import com.wingman.client.api.event.Event;
import com.wingman.client.api.event.EventListener;
import com.wingman.client.api.event.EventListenerList;

public class ExperienceGainedEvent extends Event {

    public static final EventListenerList eventListenerList = new EventListenerList();

    public int skillId;
    public int oldLevel;
    public int oldXpAmount;
    public int newXpAmount;

    public ExperienceGainedEvent(int skillId, int oldLevel, int oldXpAmount, int newXpAmount) {
        this.skillId = skillId;
        this.oldLevel = oldLevel;
        this.oldXpAmount = oldXpAmount;
        this.newXpAmount = newXpAmount;
    }

    @Override
    public EventListener[] getListeners() {
        return eventListenerList.listeners;
    }
}
