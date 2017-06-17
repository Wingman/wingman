package com.wingman.defaultplugins.devutils.util.external;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Response;
import com.wingman.client.api.net.HttpClient;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class ItemPriceCache {

    private static final Object cacheThreadMonitor = new Object();
    private static Cache<Integer, Integer> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();
    private static Queue<Integer> cacheQueue = new ConcurrentLinkedQueue<>();
    private static CacheThread cacheThread;

    public static int getItemPrice(int itemId) {
        Integer price = getCache()
                .getIfPresent(itemId);

        if (price == null) {
            if (!cacheQueue.contains(itemId)) {
                cacheQueue.add(itemId);

                synchronized (cacheThreadMonitor) {
                    cacheThreadMonitor.notify();
                }
            }

            return -1;
        }

        return price;
    }

    private static Cache<Integer, Integer> getCache() {
        if (cacheThread == null) {
            cacheThread = new CacheThread();
            cacheThread.start();
        }
        return cache;
    }

    private static class CacheThread extends Thread {
        volatile boolean running = true;

        @Override
        public void run() {
            HttpClient httpClient = new HttpClient();
            JsonParser jsonParser = new JsonParser();

            while (running) {
                Iterator<Integer> iterator = cacheQueue.iterator();

                while (iterator.hasNext()) {
                    int itemId = iterator.next();
                    try {
                        Response response = httpClient
                                .downloadUrlSync("https://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + itemId);

                        JsonElement rootElement = jsonParser
                                .parse(response.body().string());

                        if (rootElement != null) {
                            JsonObject rootObject = rootElement.getAsJsonObject();

                            long buying = rootObject
                                    .get("buying")
                                    .getAsLong();

                            long selling = rootObject
                                    .get("selling")
                                    .getAsLong();

                            int price = (int) ((buying + selling) / 2);

                            cache.put(itemId, price);
                            iterator.remove();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {
                    synchronized (cacheThreadMonitor) {
                        cacheThreadMonitor.wait();
                    }
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}