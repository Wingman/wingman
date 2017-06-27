package com.wingman.client.api.net.world;

public class WorldInfo {

    public int id;
    public int mask;
    public String host;
    public String activity;
    public int location;
    public int playerCount;

    public WorldInfo(int id, int mask, String host, String activity, int location, int playerCount) {
        this.id = id;
        this.mask = mask;
        this.host = host;
        this.activity = activity;
        this.location = location;
        this.playerCount = playerCount;
    }
}
