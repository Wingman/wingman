package com.wingman.client.api.plugin;

import com.wingman.client.api.overlay.Overlay;

import java.util.List;

public interface PluginContainer {

    Plugin getInfo();

    PluginHelper getHelper();

    List<PluginContainer> getDependencies();

    Object getInstance();

    List<Overlay> getOverlays();
}
