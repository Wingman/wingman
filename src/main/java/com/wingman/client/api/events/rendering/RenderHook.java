package com.wingman.client.api.events.rendering;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public interface RenderHook {

    ConcurrentLinkedDeque<RenderHook> renderHooks = new ConcurrentLinkedDeque<>();

    boolean render(Graphics graphics);
}
