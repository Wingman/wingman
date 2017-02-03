package com.wingman.client.plugin.toposort;

import com.wingman.client.plugin.PluginContainer;
import com.wingman.client.plugin.PluginManager;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link PluginNode} is for use with the topological sorting functionality.
 *
 * @see PluginManager#sortPlugins()
 */
public class PluginNode {

    public List<PluginNode> children = new LinkedList<>();
    public PluginContainer pluginContainer;
    public boolean isVisited;
    public boolean isDependent;

    public PluginNode(PluginContainer pluginContainer) {
        this.pluginContainer = pluginContainer;
    }

    public void addChild(PluginNode pluginNode) {
        children.add(pluginNode);
        pluginNode.isDependent = true;
    }
}
