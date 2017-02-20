package com.wingman.client.plugin;

import com.wingman.client.api.overlay.Overlay;
import com.wingman.client.api.plugin.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link PluginContainerImpl} is the base of the client-plugin layer,
 * providing the client interactability with each and every plugin.
 * <p>
 * It wraps a {@link Plugin} annotated {@link Class}.
 */
public class PluginContainerImpl implements PluginContainer {

    private final Plugin info;
    private final PluginHelper helper;
    private final List<PluginDependency> originalDependencies = new ArrayList<>();
    private final List<PluginContainer> dependencies = new ArrayList<>();
    private final Object instance;

    private final List<Overlay> overlays = new ArrayList<>();

    private final Method setupMethod;
    private final Method activateMethod;
    private final Method deactivateMethod;

    /**
     * Retrieves the annotations of a {@link Plugin} and maps them accordingly for access from the client.
     *
     * @param clazz a {@link Plugin} annotated class
     */
    public PluginContainerImpl(Class clazz) {
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

    @Override
    public Plugin getInfo() {
        return info;
    }

    @Override
    public PluginHelper getHelper() {
        return helper;
    }

    public List<PluginDependency> getOriginalDependencies() {
        return originalDependencies;
    }

    @Override
    public List<PluginContainer> getDependencies() {
        return dependencies;
    }

    @Override
    public Object getInstance() {
        return instance;
    }

    @Override
    public List<Overlay> getOverlays() {
        return overlays;
    }

    /**
     * Attempts to safely invoke the {@link PluginContainerImpl#setupMethod} of a plugin.
     *
     * @throws InvocationTargetException if invoking the setup method failed
     * @throws IllegalAccessException if invoking the setup method failed
     */
    public void setup() throws InvocationTargetException, IllegalAccessException {
        invokeMethod(setupMethod);
    }

    /**
     * Attempts to safely invoke the {@link PluginContainerImpl#activateMethod} of a plugin.
     *
     * @throws InvocationTargetException if invoking the activate method failed
     * @throws IllegalAccessException if invoking the activate method failed
     */
    public void activate() throws InvocationTargetException, IllegalAccessException {
        invokeMethod(activateMethod);
    }

    /**
     * Attempts to safely invoke the {@link PluginContainerImpl#deactivateMethod} of a plugin.
     *
     * @throws InvocationTargetException if invoking the deactivate method failed
     * @throws IllegalAccessException if invoking the deactivate method failed
     */
    public void deactivate() throws InvocationTargetException, IllegalAccessException {
        invokeMethod(deactivateMethod);
    }

    /**
     * Invokes a {@link Method}.
     *
     * @param method the method to safely invoke
     * @throws InvocationTargetException if invoking the method failed
     * @throws IllegalAccessException if invoking the method failed
     */
    private void invokeMethod(Method method) throws InvocationTargetException, IllegalAccessException {
        if (method != null) {
            method.invoke(instance);
        }
    }
}
