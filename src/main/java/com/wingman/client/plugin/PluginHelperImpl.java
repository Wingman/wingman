package com.wingman.client.plugin;

import com.google.common.io.ByteStreams;
import com.wingman.client.ClientSettings;
import com.wingman.client.api.overlay.Overlay;
import com.wingman.client.api.plugin.PluginHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

public class PluginHelperImpl implements PluginHelper {

    public PluginContainer container;

    public PluginHelperImpl(PluginContainer container) {
        this.container = container;
    }

    @Override
    public PluginContainer getContainer() {
        return container;
    }

    @Override
    public Optional<InputStream> getResourceStream(String filePath) throws IOException {
        String pluginId = container.info.id().toLowerCase();

        InputStream resourceStream = container
                .instance
                .getClass()
                .getClassLoader()
                .getResourceAsStream(pluginId + "/" + filePath);

        if (resourceStream == null) {
            resourceStream = Files.newInputStream(ClientSettings
                    .PLUGINS_DIR
                    .resolve("resources")
                    .resolve(pluginId)
                    .resolve(filePath));
        }

        if (resourceStream != null) {
            return Optional.of(resourceStream);
        }

        return Optional.empty();
    }

    @Override
    public Optional<byte[]> getResourceBytes(String filePath) throws IOException {
        Optional<InputStream> resourceStream = getResourceStream(filePath);

        if (resourceStream.isPresent()) {
            byte[] byteArray = ByteStreams
                    .toByteArray(resourceStream.get());

            if (byteArray != null) {
                return Optional.of(byteArray);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<BufferedImage> getResourceImage(String filePath) throws IOException {
        Optional<byte[]> imageBytes = getResourceBytes(filePath);

        if (imageBytes.isPresent()) {
            BufferedImage image = ImageIO
                    .read(new ByteArrayInputStream(imageBytes.get()));

            if (image != null) {
                return Optional.of(image);
            }
        }

        return Optional.empty();
    }

    @Override
    public void registerEventClass(Object classInstance) {
        PluginManager.registerEventClass(classInstance);
    }

    @Override
    public void registerOverlay(Overlay overlay) {
        container.overlays.add(overlay);
    }
}
