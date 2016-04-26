package com.wingman.client.api.events.rendering;

import java.awt.*;

public abstract class AbstractRenderHook implements RenderHook {

    private volatile boolean toRemove = false;

    public final void remove() {
        toRemove = true;
    }

    public final boolean isRemoved() {
        return toRemove;
    }

    @Override
    public final boolean render(Graphics graphics) {
        return toRemove || display(graphics);
    }

    public abstract boolean display(Graphics graphics);
}
