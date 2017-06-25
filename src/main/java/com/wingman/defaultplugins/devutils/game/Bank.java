package com.wingman.defaultplugins.devutils.game;

import com.wingman.client.api.generated.GameAPI;
import com.wingman.client.api.generated.ItemContainer;
import com.wingman.client.api.generated.NodeTable;
import com.wingman.client.api.generated.Widget;

import java.util.Optional;

/**
 * Provides API for getting data about the game bank. <br>
 * Most of the functionality only works while the bank interface/widget is opened.
 */
public class Bank {

    /**
     * Calls {@link ItemContainer#getIds()} on the bank's {@link ItemContainer}, <br>
     * and returns the returned {@code int[]}.
     *
     * @return an array containing item IDs for item slots, where an "item slot" is an array index;
     *         or nothing if the item container couldn't be retrieved
     */
    public static Optional<int[]> getItemIds() {
        return getItemContainer()
                .map(ItemContainer::getIds);
    }

    /**
     * Calls {@link ItemContainer#getQuantities()} on the bank's {@link ItemContainer}, <br>
     * and returns the returned {@code int[]}.
     *
     * @return an array containing item quantities for item slots, where an "item slot" is an array index;
     *         or nothing if the item container couldn't be retrieved
     */
    public static Optional<int[]> getItemQuantities() {
        return getItemContainer()
                .map(ItemContainer::getQuantities);
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
        return getWidgets().isPresent();
    }

    /**
     * @return the bank widget;
     *         or nothing if it can't be retrieved
     *         (which usually means that the bank isn't open)
     */
    public static Optional<Widget> getWidget() {
        try {
            return getWidgets()
                    .map(widgets -> widgets[4]);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * @return the group of bank related widgets;
     *         or nothing if it can't be retrieved
     *         (which usually means that the bank isn't open)
     */
    public static Optional<Widget[]> getWidgets() {
        try {
            return Optional.of(GameAPI.getWidgets()[12]);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * @return the bank's {@link ItemContainer};
     *         or nothing if the item container couldn't be retrieved
     */
    public static Optional<ItemContainer> getItemContainer() {
        try {
            NodeTable itemContainers = GameAPI.getItemContainers();
            return Optional.of((ItemContainer) itemContainers.get(95));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
