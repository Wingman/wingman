package com.wingman.client.api.generated;

import java.applet.Applet;
import java.awt.Canvas;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Map;

@SuppressWarnings("all")
public interface Static {
    void drawRightClickMenu();

    void executeRuneScript(RuneScript arg0, int arg1);

    ItemDefinition getItemDefinition(int arg0);

    int getVarbitValue(int arg0);

    Varp getVarpForId(int arg0);

    boolean loadWidget(int arg0);

    void openWidget(int arg0);

    void processLoginStages();

    void pushMessage(int arg0, String arg1, String arg2, String arg3);

    int getAppletHeight();

    int getAppletWidth();

    int getCameraPitch();

    int getCameraX();

    int getCameraY();

    int getCameraYaw();

    int getCameraZ();

    Canvas getCanvas();

    String getClanChatName();

    String getClanChatOwner();

    ClanMate[] getClanMates();

    int getClientPlane();

    ReferenceTable getConfigReferenceTable();

    int[] getCurrentLevels();

    int[] getExpLevels();

    int[] getExperiences();

    int getFps();

    Friend[] getFriends();

    int getFriendsSize();

    int getGameDrawingMode();

    GameEngine getGameEngine();

    Applet getGameEngineAsApplet();

    int[] getGameSettings();

    int getGameState();

    TaskHandler getGlobalTaskHandler();

    NodeDeque[][][] getGroundItems();

    NodeTable getItemContainers();

    Landscape getLandscape();

    int getLandscapeOffsetX();

    int getLandscapeOffsetY();

    int getLandscapeOffsetZ();

    int getLoadingState();

    Player getLocalPlayer();

    int getLoginState();

    int getMapAngle();

    int getMapOffset();

    int getMapScale();

    String[] getMenuActions();

    int getMenuHeight();

    String[] getMenuOptions();

    int getMenuSize();

    int getMenuWidth();

    int getMenuY();

    Map getMessageContainers();

    ImmutableNodeDeque getMessageDeque();

    int getMouseX();

    int getMouseY();

    short getMouseZoom();

    Npc[] getNpcs();

    LinkedList getOnlineFriends();

    int getPlayerIndex();

    Player[] getPlayers();

    int getRepaintChatBox();

    int getRepaintFlag();

    boolean getResizableMode();

    int getRevision();

    int getRunEnergy();

    int[] getSettings();

    DualNodeMap getSpotAnimCache();

    DualNodeMap getSpotAnimRasterizerMap();

    int[][][] getTileHeights();

    byte[][][] getTileSettings();

    String getUsername();

    int getViewPortHeight();

    int getViewPortScale();

    int getViewPortWidth();

    Widget[][] getWidgets();

    int getWorldCount();

    World[] getWorlds();

    @SuppressWarnings("all")
    interface Unsafe {
        void setAppletHeight(int value);

        void setAppletWidth(int value);

        void setCameraPitch(int value);

        void setCameraX(int value);

        void setCameraY(int value);

        void setCameraYaw(int value);

        void setCameraZ(int value);

        void setCanvas(Canvas value);

        void setClanChatName(String value);

        void setClanChatOwner(String value);

        void setClanMates(ClanMate[] value);

        void setClientPlane(int value);

        void setConfigReferenceTable(ReferenceTable value);

        void setCurrentLevels(int[] value);

        void setExpLevels(int[] value);

        void setExperiences(int[] value);

        void setFps(int value);

        void setFriends(Friend[] value);

        void setFriendsSize(int value);

        void setGameDrawingMode(int value);

        void setGameEngine(GameEngine value);

        void setGameEngineAsApplet(Applet value);

        void setGameSettings(int[] value);

        void setGameState(int value);

        void setGlobalTaskHandler(TaskHandler value);

        void setGroundItems(NodeDeque[][][] value);

        void setItemContainers(NodeTable value);

        void setLandscape(Landscape value);

        void setLandscapeOffsetX(int value);

        void setLandscapeOffsetY(int value);

        void setLandscapeOffsetZ(int value);

        void setLoadingState(int value);

        void setLocalPlayer(Player value);

        void setLoginState(int value);

        void setMapAngle(int value);

        void setMapOffset(int value);

        void setMapScale(int value);

        void setMenuActions(String[] value);

        void setMenuHeight(int value);

        void setMenuOptions(String[] value);

        void setMenuSize(int value);

        void setMenuWidth(int value);

        void setMenuY(int value);

        void setMessageContainers(Map value);

        void setMessageDeque(ImmutableNodeDeque value);

        void setMouseX(int value);

        void setMouseY(int value);

        void setMouseZoom(short value);

        void setNpcs(Npc[] value);

        void setOnlineFriends(LinkedList value);

        void setPlayerIndex(int value);

        void setPlayers(Player[] value);

        void setRepaintChatBox(int value);

        void setRepaintFlag(int value);

        void setResizableMode(boolean value);

        void setRevision(int value);

        void setRunEnergy(int value);

        void setSettings(int[] value);

        void setSpotAnimCache(DualNodeMap value);

        void setSpotAnimRasterizerMap(DualNodeMap value);

        void setTileHeights(int[][][] value);

        void setTileSettings(byte[][][] value);

        void setUsername(String value);

        void setViewPortHeight(int value);

        void setViewPortScale(int value);

        void setViewPortWidth(int value);

        void setWidgets(Widget[][] value);

        void setWorldCount(int value);

        void setWorlds(World[] value);
    }
}
