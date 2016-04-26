package com.wingman.client.api.plugin;

import com.wingman.client.plugin.PluginContainer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface PluginHelper {

    PluginContainer getContainer();

    String getPluginResourceDir();

    InputStream getFileStreamFromResourceDirRoot(String path) throws IOException;

    byte[] getFileFromResourceDirRoot(String path) throws IOException;

    BufferedImage getImageFromResourceDirRoot(String path) throws IOException;

    Set<String> getDependencyResourceDirs();
}
