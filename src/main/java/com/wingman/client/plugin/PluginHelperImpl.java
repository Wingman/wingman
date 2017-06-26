package com.wingman.client.plugin;

import com.google.common.io.ByteStreams;
import com.wingman.client.ClientSettings;
import com.wingman.client.api.overlay.Overlay;
import com.wingman.client.api.plugin.PluginHelper;
import com.wingman.client.api.ui.settingscreen.SettingsSection;
import com.wingman.client.ui.Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class PluginHelperImpl implements PluginHelper {

    public PluginContainerImpl container;

    public PluginHelperImpl(PluginContainerImpl container) {
        this.container = container;
    }

    @Override
    public PluginContainerImpl getContainer() {
        return container;
    }

    @Override
    public Optional<InputStream> getResourceStream(String filePath) throws IOException {
        String pluginId = container
                .getInfo()
                .id()
                .toLowerCase();

        InputStream resourceStream = container
                .getInstance()
                .getClass()
                .getClassLoader()
                .getResourceAsStream(pluginId + "/" + filePath);

        if (resourceStream == null) {
            Path path = ClientSettings
                    .PLUGINS_DIR
                    .resolve("resources")
                    .resolve(pluginId)
                    .resolve(filePath);

            if (Files.exists(path)) {
                resourceStream = Files.newInputStream(path);
            }
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
    public OutputStream getResourceOutputStream(String filePath) throws IOException {
        String pluginId = container
                .getInfo()
                .id()
                .toLowerCase();

        URL resourceUrl = container
                .getInstance()
                .getClass()
                .getClassLoader()
                .getResource(pluginId + "/" + filePath);

        File resourcePath;

        if (resourceUrl == null) {
            resourcePath = ClientSettings
                    .PLUGINS_DIR
                    .resolve("resources")
                    .resolve(pluginId)
                    .resolve(filePath)
                    .toFile();
        } else {
            try {
                resourcePath = new File(resourceUrl.toURI());
            } catch (URISyntaxException e) {
                // This should never happen
                throw new RuntimeException(e);
            }
        }

        if (!resourcePath.exists()) {
            resourcePath.getParentFile().mkdirs();
            resourcePath.createNewFile();
        }

        return Files.newOutputStream(resourcePath.toPath());
    }

    @Override
    public void registerEventClass(Object classInstance) {
        PluginManager.registerEventClass(classInstance);
    }

    @Override
    public void registerOverlay(Overlay overlay) {
        container.getOverlays().add(overlay);
    }

    @Override
    public void registerSettingsSection(SettingsSection settingsSection) {
        Client.settingsScreen.registerSection(settingsSection);
    }
}
