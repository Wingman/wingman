package com.wingman.client.plugin;

import com.google.common.base.Throwables;
import com.wingman.client.api.plugin.Plugin;
import com.wingman.client.api.plugin.PluginDependency;
import com.wingman.client.api.plugin.PluginDependencies;

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

    public final Plugin pluginData;
    public final List<PluginDependency> originalDependencies = new ArrayList<>();
    public final List<PluginContainer> dependencies = new ArrayList<>();
    public final Object instance;
    private final Method setupMethod;
    private final Method activateMethod;
    private final Method refreshMethod;
    private final Method deactivateMethod;

    /**
     * Retrieves the annotations of a {@link Plugin} and maps them accordingly for access from the client.
     *
     * @param clazz a {@link Plugin} annotated class
     */
    public PluginContainer(Class clazz) {
            try {
            pluginData = (Plugin) clazz.getAnnotation(Plugin.class);

            if (clazz.isAnnotationPresent(PluginDependencies.class)) {
                originalDependencies.addAll(Arrays.asList(((PluginDependencies) clazz.getAnnotation(PluginDependencies.class)).value()));
            } else if(clazz.isAnnotationPresent(PluginDependency.class)) {
                originalDependencies.add((PluginDependency) clazz.getAnnotation(PluginDependency.class));
            }

            Method setupMethod = null;
            Method activateMethod = null;
            Method refreshMethod = null;
            Method deactivateMethod = null;
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(Plugin.Setup.class)) {
                    setupMethod = method;
                } else if (method.isAnnotationPresent(Plugin.Activate.class)) {
                    activateMethod = method;
                } else if (method.isAnnotationPresent(Plugin.Refresh.class)) {
                    refreshMethod = method;
                } else if (method.isAnnotationPresent(Plugin.Deactivate.class)) {
                    deactivateMethod = method;
                }
            }
            this.setupMethod = setupMethod;
            this.activateMethod = activateMethod;
            this.refreshMethod = refreshMethod;
            this.deactivateMethod = deactivateMethod;

            instance = clazz.newInstance();

            for (Field field : clazz.getFields()) {
                if (field.isAnnotationPresent(Plugin.Helper.class)) {
                    field.setAccessible(true);
                    field.set(instance, new PluginHelperImpl(this));
                }
            }
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    /**
     * Attempts to safely invoke the {@link PluginContainer#setupMethod} of a plugin.
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void invokeSetupMethod() throws InvocationTargetException, IllegalAccessException {
        invokeSafe(setupMethod);
    }

    /**
     * Attempts to safely invoke the {@link PluginContainer#activateMethod} of a plugin.
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void invokeActivateMethod() throws InvocationTargetException, IllegalAccessException {
        invokeSafe(activateMethod);
    }

    /**
     * Attempts to safely invoke the {@link PluginContainer#refreshMethod} of a plugin.
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void invokeRefreshMethod() throws InvocationTargetException, IllegalAccessException {
        invokeSafe(refreshMethod);
    }

    /**
     * Attempts to safely invoke the {@link PluginContainer#deactivateMethod} of a plugin.
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void invokeDeactivateMethod() throws InvocationTargetException, IllegalAccessException {
        invokeSafe(deactivateMethod);
    }

    /**
     * Safely invokes a {@link Method}.
     *
     * @param method the method to safely invoke
     */
    private void invokeSafe(Method method) {
        try {
            if (method != null) {
                method.invoke(instance);
            }
        } catch (Exception e) {
            Throwables.propagate(e);
        }
    }

    public Method getSetupMethod() {
        return setupMethod;
    }

    public Method getActivateMethod() {
        return activateMethod;
    }

    public Method getRefreshMethod() {
        return refreshMethod;
    }

    public Method getDeactivateMethod() {
        return deactivateMethod;
    }
}
