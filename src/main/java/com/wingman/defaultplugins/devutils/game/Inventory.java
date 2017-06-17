package com.wingman.defaultplugins.devutils.game;

import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.generated.Widget;

/**
 * Provides API for getting data about the local player's inventory.
 */
public class Inventory {

    /**
     * @return an array containing item IDs for item slots, where an "item slot" is an array index;
     *         or an empty array if the inventory can't be looked up
     */
    public static int[] getItemIds() {
        Widget widget = getWidget();

        if (widget != null) {
            return widget.getItemIds();
        }

        return new int[0];
    }

    /**
     * @return an array containing item quantities for item slots, where an "item slot" is an array index;
     *         or an empty array if the inventory can't be looked up
     */
    public static int[] getItemQuantities() {
        Widget widget = getWidget();

        if (widget != null) {
            return widget.getItemQuantities();
        }

        return new int[0];
    }

    /**
     * @param itemId the item ID of the item to lookup
     * @return {@code true} if the item was successfully found in the inventory;
     *         {@code false} if it was not found
     */
    public static boolean containsItem(int itemId) {
        for (int id : getItemQuantities()) {
            if (id == itemId) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param itemId the item ID of the item to count
     * @return the amount of the item ID in the inventory
     */
    public static int countItem(int itemId) {
        int count = 0;

        int[] ids = getItemIds();
        int[] quantities = getItemQuantities();

        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == itemId) {
                count += quantities[i];
            }
        }

        return count;
    }

    /**
     * @return the amount of slots that are occupied with an item
     */
    public static int getUsedSpace() {
        int count = 0;

        for (int id : getItemIds()) {
            if (id != 0) {
                count++;
            }
        }

        return count;
    }

    /**
     * @return the amount of slots that aren't occupied with an item
     */
    public static int getFreeSpace() {
        return getCapacity() - getUsedSpace();
    }

    /**
     * @return the maximum capacity of the inventory
     */
    public static int getCapacity() {
        return 28;
    }

    /**
     * @return the inventory widget;
     *         or {@code null} if the widget couldn't be retrieved
     */
    public static Widget getWidget() {
        try {
            return GameAPI.getWidgets()[149][0];
        } catch (Exception e) {
            return null;
        }
    }
}
