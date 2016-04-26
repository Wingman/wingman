package com.wingman.client.plugin;

import com.wingman.client.api.plugin.PluginHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class PluginHelperImpl implements PluginHelper {

    public PluginContainer container;

    public PluginHelperImpl(PluginContainer container) {
        this.container = container;
    }

    /**
     * Returns the plugin container of the plugin.
     *
     * @return the plugin's {@link PluginContainer}
     */
    @Override
    public PluginContainer getContainer() {
        return container;
    }

    /**
     * Returns the plugins own resource directory: "/resources/plugin id in lowercase/" <br>
     *
     * To be used in {@link java.lang.Class#getResource(String)} or {@link java.lang.Class#getResourceAsStream(String)} from within the scope of the plugin.
     *
     * @return the resource directory
     */
    @Override
    public String getPluginResourceDir() {
        return "/resources/" + container.pluginData.id().toLowerCase() + "/";
    }

    /**
     * Returns a {@link InputStream} representation of the passed file path. <br>
     * The path can possibly start with /resources/plugin id in lowercase/... as returned by {@link PluginHelperImpl#getPluginResourceDir()}. <br>
     * Where ... is the file that is searched for. <br>
     *
     * The file should exist in the class loader of the plugin.
     *
     * @param path a path to a resource file belonging to a plugin
     * @return an {@link InputStream} of the found resource file,
     *         or {@code null} if the file was not found
     * @throws IOException
     */
    @Override
    public InputStream getFileStreamFromResourceDirRoot(String path) throws IOException {
        return container.instance.getClass().getResourceAsStream(path);
    }

    /**
     * Returns a {@code byte[]} representation of the passed file path. <br>
     * The path can possibly start with /resources/plugin id in lowercase/... as returned by {@link PluginHelperImpl#getPluginResourceDir()}. <br>
     * Where ... is the file that is searched for. <br>
     *
     * The file should exist in the class loader of the plugin.
     *
     * @param path a path to a resource file belonging to a plugin
     * @return a {@code byte[]} representation of the found resource file
     *         or {@code null} if the file was not found
     * @throws IOException
     */
    @Override
    public byte[] getFileFromResourceDirRoot(String path) throws IOException {
        InputStream inputStream = getFileStreamFromResourceDirRoot(path);
        if (inputStream != null) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            int read;
            byte[] data = new byte[4096];
            while ((read = inputStream.read(data, 0, data.length)) != -1) {
                output.write(data, 0, read);
            }
            inputStream.close();
            return output.toByteArray();
        }
        return null;
    }

    /**
     * Returns a {@link BufferedImage} representation of the passed file path. <br>
     * The path can possibly start with /resources/plugin id in lowercase/... as returned by {@link PluginHelperImpl#getPluginResourceDir()}. <br>
     * Where ... is the image file that is searched for. <br>
     *
     * The file should exist in the class loader of the plugin.
     *
     * @param path a path to an image resource file belonging to a plugin
     * @return a {@link BufferedImage} representation of the found resource file
     *         or {@code null} if the file was not found
     * @throws IOException
     */
    @Override
    public BufferedImage getImageFromResourceDirRoot(String path) throws IOException {
        byte[] image = getFileFromResourceDirRoot(path);
        if (image != null) {
            return ImageIO.read(new ByteArrayInputStream(image));
        }
        return null;
    }

    /**
     * Iterates through the valid dependencies of the plugin, and compiles a {@link Set} of their individual resource directories.
     *
     * @see PluginHelperImpl#getPluginResourceDir()
     *
     * @return {@link Set} of dependency resource directories
     */
    @Override
    public Set<String> getDependencyResourceDirs() {
        Set<String> dependencyResourceDirs = new HashSet<>();
        for (PluginContainer pluginContainer : container.dependencies) {
            dependencyResourceDirs.add("/resources/" + pluginContainer.pluginData.id().toLowerCase() + "/");
        }
        return dependencyResourceDirs;
    }
}