package com.wingman.client.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Put in place for future class loading intercepting.
 */
public class PluginClassLoader extends URLClassLoader {

    public PluginClassLoader(URL[] urls, TransformingClassLoader parent) {
        super(urls, parent);

        for (URL url : urls) {
            parent.addURL(url);
        }
    }
}
