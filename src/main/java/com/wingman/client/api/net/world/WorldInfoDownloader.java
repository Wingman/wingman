package com.wingman.client.api.net.world;

import okhttp3.Response;
import com.wingman.client.api.net.HttpClient;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class WorldInfoDownloader {

    private static final String WORLD_LIST_URL = "http://www.runescape.com/g=oldscape/slr.ws?order=LPWM";

    private static HttpClient httpClient = new HttpClient();

    public static List<WorldInfo> downloadWorldInfo() throws IOException {
        List<WorldInfo> worlds = new ArrayList<>();

        Response response = httpClient
                .downloadUrlSync(WORLD_LIST_URL);
        
        ByteBuffer buffer = ByteBuffer.wrap(response.body().bytes());

        // skip 4 bytes
        buffer.position(4);

        int worldCount = buffer.getShort() & 0xFFFF;

        for (int i = 0; i < worldCount; i++) {
            int id = buffer.getShort() & 0xFFFF;
            int mask = buffer.getInt();
            String host = readString(buffer);
            String activity = readString(buffer);
            int location = buffer.get() & 0xFF;
            int playerCount = buffer.getShort();

            worlds.add(new WorldInfo(id, mask, host, activity, location, playerCount));
        }

        return worlds;
    }

    private static String readString(ByteBuffer buffer) {
        StringBuilder stringBuilder = new StringBuilder();

        while (buffer.hasRemaining()) {
            byte character = buffer.get();

            if (character == 0) {
                break;
            }

            stringBuilder.append((char) character);
        }

        return stringBuilder.toString();
    }
}
