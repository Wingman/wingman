package com.wingman.client.api.plugin;

import com.wingman.client.api.overlay.Overlay;
import com.wingman.client.plugin.PluginContainerImpl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface PluginHelper {

    PluginContainerImpl getContainer();

    Optional<InputStream> getResourceStream(String filePath) throws IOException;

    Optional<byte[]> getResourceBytes(String filePath) throws IOException;

    Optional<BufferedImage> getResourceImage(String filePath) throws IOException;

    void registerEventClass(Object classInstance);

    void registerOverlay(Overlay overlay);
}
