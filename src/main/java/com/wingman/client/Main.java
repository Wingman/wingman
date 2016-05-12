package com.wingman.client;

import com.google.common.base.Throwables;
import com.wingman.client.classloader.TransformingClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;

public class Main {

    // WARNING: This class can't touch any of our code!
    /**
     * The main entry point of the client. <br>
     * It invokes the main entry point of {@link RelaunchedMain} through a {@link TransformingClassLoader} with the purpose of future self injection.
     *
     * @param args application arguments, passed to {@link RelaunchedMain}
     */
    public static void main(String[] args) {
        URLClassLoader classLoader = (URLClassLoader) Main.class.getClassLoader();
        try {
            new TransformingClassLoader(classLoader.getURLs(), classLoader)
                    .loadClass(RelaunchedMain.class.getName())
                    .getMethods()[0]
                    .invoke(null, new Object[]{args});
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            Throwables.propagate(e);
        }
    }
}
