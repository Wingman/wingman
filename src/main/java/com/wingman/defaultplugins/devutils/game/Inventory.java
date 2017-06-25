package com.wingman.defaultplugins.devutils.game;

import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.generated.Widget;

import java.util.Optional;

/**
 * Provides API for getting data about the local player's inventory.
 */
public class Inventory {

    /**
     * @return an array containing item IDs for item slots, where an "item slot" is an array index;
     *         or nothing if the inventory can't be looked up
     */
    public static Optional<int[]> getItemIds() {
        return getWidget()
                .map(Widget::getItemIds);
    }

    /**
     * @return an array containing item quantities for item slots, where an "item slot" is an array index;
     *         or nothing if the inventory can't be looked up
     */
    public static Optional<int[]> getItemQuantities() {
        return getWidget()
                .map(Widget::getItemQuantities);
    }

    /**
     * @param itemId the item ID of the item to lookup
     * @return {@code true} if the item was successfully found in the inventory;
     *         {@code false} if it was not found;
     *         or nothing if the inventory can't be looked up
     */
    public static Optional<Boolean> containsItem(int itemId) {
        Optional<int[]> quantities = getItemQuantities();

        if (quantities.isPresent()) {
            for (int id : quantities.get()) {
                if (id == itemId) {
                    return Optional.of(true);
                }
            }

            return Optional.of(false);
        }

        return Optional.empty();
    }

    /**
     * @param itemId the item ID of the item to count
     * @return the amount of the item ID in the inventory;
     *         or nothing if the inventory can't be looked up
     */
    public static Optional<Integer> countItem(int itemId) {
        Optional<int[]> ids = getItemIds();
        Optional<int[]> quantities = getItemQuantities();

        if (ids.isPresent() && quantities.isPresent()) {
            int count = 0;

            for (int i = 0; i < ids.get().length; i++) {
                if (ids.get()[i] == itemId) {
                    count += quantities.get()[i];
                }
            }

            return Optional.of(count);
        }

        return Optional.empty();
    }

    /**
     * @return the amount of slots that are occupied with an item;
     *         or nothing if the inventory can't be looked up
     */
    public static Optional<Integer> getUsedSpace() {
        Optional<int[]> ids = getItemIds();

        if (ids.isPresent()) {
            int count = 0;

            for (int id : ids.get()) {
                if (id != 0) {
                    count++;
                }
            }

            return Optional.of(count);
        }

        return Optional.empty();
    }

    /**
     * @return the amount of slots that aren't occupied with an item;
     *         or nothing if the inventory can't be looked up
     */
    public static Optional<Integer> getFreeSpace() {
        return getUsedSpace()
                .map(usedSpace -> getCapacity() - usedSpace);
    }

    /**
     * @return the maximum capacity of the inventory
     */
    public static int getCapacity() {
        return 28;
    }

    /**
     * @return the inventory widget;
     *         or nothing if the widget couldn't be retrieved
     */
    public static Optional<Widget> getWidget() {
        try {
            return Optional.of(GameAPI.getWidgets()[149][0]);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
