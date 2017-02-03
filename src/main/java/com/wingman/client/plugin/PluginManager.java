package com.wingman.client.plugin;

import com.github.zafarkhaja.semver.Version;
import com.google.common.io.Files;
import com.wingman.client.ClientSettings;
import com.wingman.client.api.event.Event;
import com.wingman.client.api.event.EventCallback;
import com.wingman.client.api.event.EventListener;
import com.wingman.client.api.event.EventListenerList;
import com.wingman.client.api.overlay.Overlay;
import com.wingman.client.api.plugin.Plugin;
import com.wingman.client.api.plugin.PluginDependency;
import com.wingman.client.classloader.PluginClassLoader;
import com.wingman.client.plugin.exceptions.PluginActivationException;
import com.wingman.client.plugin.exceptions.PluginDeActivationException;
import com.wingman.client.plugin.exceptions.PluginSetupException;
import com.wingman.client.plugin.toposort.PluginNode;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

public final class PluginManager {

    private static List<PluginContainer> plugins = new ArrayList<>();

    private static Reflections pluginClassLoaderReflections;
    private static Set<Class<? extends Event>> eventClasses = new HashSet<>();

    /**
     * Loads all plugins annotated with {@link Plugin} found by the client and plugin class loader.
     * <p>
     * This method should only be called once ever.
     */
    public static void findAndSetupPlugins() throws Exception {
        System.out.println("Finding and setting up plugins");

        pluginClassLoaderReflections = new Reflections(getPluginClassLoader());

        Set<Class<?>> pluginClasses = pluginClassLoaderReflections
                .getTypesAnnotatedWith(Plugin.class);
        plugins = parsePlugins(pluginClasses);

        pluginClassLoaderReflections = null;

        parsePluginDependencies();
        sortPlugins();

        setupPlugins();

        bakeEventListeners();
    }

    /**
     * Adds all folders in {@link ClientSettings#PLUGINS_DIR},
     * and all of its containing JARs to a {@link PluginClassLoader}.
     *
     * @return a class loader with the supposed plugins within {@link ClientSettings#PLUGINS_DIR}
     */
    private static PluginClassLoader getPluginClassLoader() throws IOException {
        Set<URL> pluginUrls = new HashSet<>();

        // Search for folders in the root path
        File[] pluginsRootFolderFiles = ClientSettings.PLUGINS_DIR.toFile().listFiles();
        if (pluginsRootFolderFiles != null) {
            for (File file : pluginsRootFolderFiles) {
                if (file.isDirectory()) {
                    pluginUrls.add(file.toURI().toURL());
                }
            }
        }

        // Search for JARed files
        for (File file : Files.fileTreeTraverser().preOrderTraversal(ClientSettings.PLUGINS_DIR.toFile())) {
            if (Files.getFileExtension(file.getName()).equalsIgnoreCase("jar")) {
                pluginUrls.add(new URL("jar:" + file.toURI().toString() + "!/"));
            }
        }

        return new PluginClassLoader(pluginUrls.toArray(new URL[pluginUrls.size()]),
                Plugin.class.getClassLoader());
    }

    /**
     * Constructs a list of {@link PluginContainer} out of a collection of classes supposed to be plugin classes.
     *
     * @param plugins a collection of plugin classes
     * @return a list containing {@link PluginContainer}-wrapped classes
     */
    private static List<PluginContainer> parsePlugins(Collection<Class<?>> plugins) {
        List<PluginContainer> pluginContainers = new LinkedList<>();
        Set<String> parsedPlugins = new HashSet<>();

        for (Class clazz : plugins) {
            PluginContainer pluginContainer = new PluginContainer(clazz);

            if (!parsedPlugins.contains(pluginContainer.info.id())) {
                parsedPlugins.add(pluginContainer.info.id());
                pluginContainers.add(pluginContainer);
            } else {
                System.out.println(MessageFormat.format(
                        "{0} was not loaded, because a plugin with the same ID had already been loaded.",
                        pluginContainer.info.id()));
            }
        }

        return pluginContainers;
    }

    /**
     * Parses dependencies ({@link PluginDependency}) of plugins and attempts to match them with loaded plugins.
     */
    private static void parsePluginDependencies() {
        Map<String, PluginContainer> pluginContainerMap = new HashMap<>();

        for (PluginContainer pluginContainer : plugins) {
            pluginContainerMap.put(pluginContainer.info.id(), pluginContainer);
        }

        Iterator<PluginContainer> pluginIterator = plugins.iterator();
        while (pluginIterator.hasNext()) {
            PluginContainer pluginContainer = pluginIterator.next();

            boolean shouldRemove = false;

            for (PluginDependency pluginDependency : pluginContainer.originalDependencies) {
                PluginContainer dependencyContainer = pluginContainerMap
                        .get(pluginDependency.id());

                if (dependencyContainer != null) {
                    Version dependencyVersion = Version
                            .valueOf(dependencyContainer.info.version());

                    if (dependencyVersion
                            .satisfies(pluginDependency.version())) {
                        pluginContainer.dependencies.add(dependencyContainer);
                    } else {
                        System.err.println(MessageFormat.format(
                                "Plugin {0} depends on {1} {2}, found {3}",
                                pluginContainer.info.id(),
                                dependencyContainer.info.id(),
                                pluginDependency.version(),
                                dependencyContainer.info.version()));

                        shouldRemove = true;
                    }
                } else {
                    System.err.println(MessageFormat.format(
                            "Plugin {0} depends on {1} {2}, which could not be found",
                            pluginContainer.info.id(),
                            pluginDependency.id(),
                            pluginDependency.version()));

                    shouldRemove = true;
                }
            }

            if (shouldRemove) {
                pluginIterator.remove();
            }
        }
    }

    /**
     * Sort the loading order of plugins topologically based on dependencies,
     * as to introduce loading dependencies before dependants.
     */
    private static void sortPlugins() {
        Map<String, PluginNode> pluginNodes = new HashMap<>();
        for (PluginContainer pluginContainer : plugins) {
            pluginNodes.put(pluginContainer.info.id(), new PluginNode(pluginContainer));
        }

        plugins = new ArrayList<>(pluginNodes.size() + 1);

        for (PluginNode pluginNode : pluginNodes.values()) {
            for (PluginContainer dependencyPlugin : pluginNode.pluginContainer.dependencies) {
                pluginNode.addChild(pluginNodes.get(dependencyPlugin.info.id()));
            }
        }

        List<PluginNode> stack = new ArrayList<>(pluginNodes.size() + 1);

        for (PluginNode pluginNode : pluginNodes.values()) {
            if (!pluginNode.isVisited && !pluginNode.isDependent) {
                sort(pluginNode, stack);
            }
        }

        for (PluginNode pluginNode : stack) {
            plugins.add(pluginNode.pluginContainer);
        }
    }

    /**
     * Sort the loading order of plugins topologically based on dependencies,
     * as to introduce loading dependencies before dependants.
     */
    private static void sort(PluginNode pluginNode, List<PluginNode> stack) {
        for (PluginNode children : pluginNode.children) {
            if (!children.isVisited) {
                sort(children, stack);
            }
        }
        stack.add(pluginNode);
        pluginNode.isVisited = true;
    }

    /**
     * Bakes event listeners, transforming every set of event listeners into arrays for faster read access.
     * <p>
     * This method should only be called once ever.
     */
    private static void bakeEventListeners() {
        for (Class<? extends Event> eventClass : eventClasses) {
            try {
                EventListenerList eventListenerList = (EventListenerList) eventClass
                        .getDeclaredField("eventListenerList")
                        .get(null);
                eventListenerList.bake();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Registers a class that contains {@link EventCallback} annotated methods, and maps those methods correctly.
     * <p>
     * This method must only be called within {@link com.wingman.client.api.plugin.Plugin.Setup}
     * or in a constructor/initializer of a plugin.
     *
     * @param instance an instance of a class containing {@link EventCallback} annotated methods
     */
    @SuppressWarnings("unchecked")
    public static void registerEventClass(final Object instance) {
        for (final Method method : instance.getClass().getMethods()) {
            if (method.isAnnotationPresent(EventCallback.class)) {
                try {
                    Class<?>[] argTypes = method.getParameterTypes();
                    if (argTypes.length > 0) {
                        Class<?> checkClass;
                        if (Event.class.isAssignableFrom(checkClass = argTypes[0])) {
                            final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);

                            Set subTypesOfEventClass = pluginClassLoaderReflections
                                    .getSubTypesOf(eventClass);

                            if (!subTypesOfEventClass.isEmpty()) {
                                for (Object subClassObject : subTypesOfEventClass) {
                                    Class<? extends Event> subClass = (Class<? extends Event>) subClassObject;
                                    registerEventClass(instance, method, subClass);
                                }
                            }

                            registerEventClass(instance, method, eventClass);
                        }
                    }
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void registerEventClass(Object instance,
                                           Method method,
                                           Class<? extends Event> eventClass)
            throws ReflectiveOperationException {

        try {
            EventListenerList eventListenerList = (EventListenerList) eventClass
                    .getDeclaredField("eventListenerList")
                    .get(null);

            eventClasses.add(eventClass);

            eventListenerList.register(new EventListener() {
                @Override
                public void runEvent(Event event) {
                    try {
                        method.invoke(instance, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (NoSuchFieldException ignored) {
        }
    }

    /**
     * Propagates an {@link Event} to its event listeners.
     *
     * @param event the event object that should be propagated to its listeners
     */
    public static void callEvent(Event event) {
        EventListener[] listeners = event.getListeners();
        if (listeners != null) {
            for (EventListener eventListener : listeners) {
                if (eventListener != null) {
                    eventListener.runEvent(event);
                }
            }
        }
    }

    /**
     * Sets all plugins up.
     *
     * Safely invokes ({@link PluginContainer#setupMethod}) of all detected plugins.
     */
    private static void setupPlugins() {
        for (PluginContainer plugin : plugins) {
            try {
                plugin.setup();
            } catch (InvocationTargetException | IllegalAccessException e) {
                new PluginSetupException(plugin.info.id(), e.toString())
                        .printStackTrace();
            }
        }
    }

    /**
     * Activates all plugins.
     * <p>
     * Attempts to activate plugins from the directory. If the plugin is
     * toggleable then it will add a new item to the settings panel.
     */
    public static void activatePlugins() {
        for (PluginContainer plugin : plugins) {
            try {
                activatePlugin(plugin);
            } catch (Exception e) {
                new PluginActivationException(plugin.info.id(), e.toString())
                        .printStackTrace();
            }
        }
    }

    /**
     * Activates a specific plugin.
     * <p>
     * If the plugin fails to activate the settings toggle will be disabled.
     *
     * @param plugin the plugin that should be activated
     * @return {@code true} if the plugin was successfully activated;
     *         {@code false} otherwise
     */
    public static boolean activatePlugin(PluginContainer plugin) throws InvocationTargetException,IllegalAccessException {
        plugin.activate();
        System.out.println(MessageFormat.format("Activated plugin {0} ({1} {2})",
                plugin.info.name(),
                plugin.info.id(),
                plugin.info.version()));
        return true;
    }

    /**
     * Deactivate all plugins.
     */
    public static void deactivatePlugins() {
        for (PluginContainer plugin : plugins) {
            deactivatePlugin(plugin);
        }
    }

    /**
     * Deactivates a specific plugin.
     *
     * @param plugin the plugin that should be deactivated
     */
    public static void deactivatePlugin(PluginContainer plugin) {
        try {
            plugin.deactivate();
            System.out.println(MessageFormat.format("Deactivated plugin {0} ({1} {2})",
                    plugin.info.name(),
                    plugin.info.id(),
                    plugin.info.version()));
        } catch (Exception e) {
            new PluginDeActivationException(plugin.info.id(), e.toString())
                    .printStackTrace();
        }
    }

    public static List<Overlay> getAllOverlays() {
        List<Overlay> overlays = new ArrayList<>();

        for (PluginContainer plugin : plugins) {
            for (Overlay overlay : plugin.overlays) {
                overlays.add(overlay);
            }
        }

        return overlays;
    }

    private PluginManager() {
        // This class should not be instantiated
    }
}
