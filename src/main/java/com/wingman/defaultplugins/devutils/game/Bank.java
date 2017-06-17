package com.wingman.defaultplugins.devutils.game;

import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.generated.ItemContainer;
import com.wingman.client.api.generated.NodeTable;
import com.wingman.client.api.generated.Widget;

/**
 * Provides API for getting data about the game bank. <br>
 * Most of the functionality only works while the bank interface/widget is opened.
 */
public class Bank {

    /**
     * Calls {@link ItemContainer#getIds()} on the bank's {@link ItemContainer}, <br>
     * and returns the returned {@code int[]}.
     *
     * @return an array containing item IDs for item slots, where an "item slot" is an array index
     */
    public static int[] getItemIds() {
        return getItemContainer().getIds();
    }

    /**
     * Calls {@link ItemContainer#getQuantities()} on the bank's {@link ItemContainer}, <br>
     * and returns the returned {@code int[]}.
     *
     * @return an array containing item quantities for item slots, where an "item slot" is an array index
     */
    public static int[] getItemQuantities() {
        return getItemContainer().getQuantities();
    }

    /**
     * @return the currently selected bank tab
     */
    public static int getSelectedTab() {
        return GameAPI.getGameSettings()[115] / 4;
    }

    /**
     * @return {@code true} if the bank is open;
     *         {@code false} otherwise
     */
    public static boolean isOpen() {
        return getWidgets() != null;
    }

    /**
     * @return the bank widget
     */
    public static Widget getWidget() {
        return getWidgets()[4];
    }

    /**
     * @return the group of bank related widgets
     */
    public static Widget[] getWidgets() {
        return GameAPI.getWidgets()[12];
    }

    /**
     * @return the bank's {@link ItemContainer}
     */
    public static ItemContainer getItemContainer() {
        NodeTable itemContainers = GameAPI.getItemContainers();
        return (ItemContainer) itemContainers.get(95);
    }
}
