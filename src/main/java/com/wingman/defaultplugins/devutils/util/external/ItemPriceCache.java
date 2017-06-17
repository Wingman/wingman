package com.wingman.defaultplugins.devutils.util.external;

import java.util.HashMap;
import java.util.Map;

public class ItemPriceCache {

    private static Map<Integer, Integer> cache = new HashMap<>();

    /**
     * Attempts to load the price of an item from the cache.
     *
     * @param itemId an item id
     * @return the average G.E price of the item;
     *         or 0 if it is not on the G.E/not in the cache
     */
    public static int getItemPrice(int itemId) {
        Integer price = cache.get(itemId);
        return price != null ? price : 0;
    }

    static {
        try {
            System.out.println("Downloading cached item prices");

            // TODO: Implement downloading cached item prices

            System.out.println("Done downloading cached item prices");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
