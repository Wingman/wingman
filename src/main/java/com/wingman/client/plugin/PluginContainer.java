package com.wingman.client.plugin;

import com.wingman.client.api.overlay.Overlay;
import com.wingman.client.api.plugin.Plugin;
import com.wingman.client.api.plugin.PluginDependencies;
import com.wingman.client.api.plugin.PluginDependency;
import com.wingman.client.api.plugin.PluginHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link PluginContainer} is the base of the client-plugin layer, providing the client interactability with each and every plugin. <br>
 * It wraps a {@link Plugin} annotated {@link Class}.
 */
public class PluginContainer {

    public final Plugin info;
    public final PluginHelper helper;
    public final List<PluginDependency> originalDependencies = new ArrayList<>();
    public final List<PluginContainer> dependencies = new ArrayList<>();
    public final Object instance;

    public final List<Overlay> overlays = new ArrayList<>();

    private final Method setupMethod;
    private final Method activateMethod;
    private final Method deactivateMethod;

    /**
     * Retrieves the annotations of a {@link Plugin} and maps them accordingly for access from the client.
     *
     * @param clazz a {@link Plugin} annotated class
     */
    public PluginContainer(Class clazz) {
        try {
            info = (Plugin) clazz.getAnnotation(Plugin.class);

            if (clazz.isAnnotationPresent(PluginDependencies.class)) {
                originalDependencies.addAll(Arrays.asList(((PluginDependencies) clazz.getAnnotation(PluginDependencies.class)).value()));
            } else if(clazz.isAnnotationPresent(PluginDependency.class)) {
                originalDependencies.add((PluginDependency) clazz.getAnnotation(PluginDependency.class));
            }

            Method setupMethod = null;
            Method activateMethod = null;
            Method deactivateMethod = null;

            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(Plugin.Setup.class)) {
                    setupMethod = method;
                } else if (method.isAnnotationPresent(Plugin.Activate.class)) {
                    activateMethod = method;
                } else if (method.isAnnotationPresent(Plugin.Deactivate.class)) {
                    deactivateMethod = method;
                }
            }

            this.setupMethod = setupMethod;
            this.activateMethod = activateMethod;
            this.deactivateMethod = deactivateMethod;

            instance = clazz.newInstance();

            helper = new PluginHelperImpl(this);

            for (Field field : clazz.getFields()) {
                if (field.isAnnotationPresent(Plugin.Helper.class)) {
                    field.setAccessible(true);
                    field.set(instance, helper);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Attempts to safely invoke the {@link PluginContainer#setupMethod} of a plugin.
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void setup() throws InvocationTargetException, IllegalAccessException {
        invokeMethod(setupMethod);
    }

    /**
     * Attempts to safely invoke the {@link PluginContainer#activateMethod} of a plugin.
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void activate() throws InvocationTargetException, IllegalAccessException {
        invokeMethod(activateMethod);
    }

    /**
     * Attempts to safely invoke the {@link PluginContainer#deactivateMethod} of a plugin.
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void deactivate() throws InvocationTargetException, IllegalAccessException {
        invokeMethod(deactivateMethod);
    }

    /**
     * Invokes a {@link Method}.
     *
     * @param method the method to safely invoke
     */
    private void invokeMethod(Method method) throws InvocationTargetException, IllegalAccessException {
        if (method != null) {
            method.invoke(instance);
        }
    }
}
