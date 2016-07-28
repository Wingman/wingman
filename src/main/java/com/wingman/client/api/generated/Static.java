package com.wingman.client.api.generated;

import java.applet.Applet;
import java.awt.Canvas;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Map;

@SuppressWarnings("all")
public interface Static {
    void executeRuneScript(RuneScript arg0, int arg1);

    void drawRightClickMenu();

    void processLoginStages();

    ItemDefinition getItemDefinition(int arg0);

    Varp getVarpForId(int arg0);

    void pushMessage(int arg0, String arg1, String arg2, String arg3);

    int getVarbitValue(int arg0);

    void openWidget(int arg0);

    boolean loadWidget(int arg0);

    DualNodeMap getSpotAnimCache();

    DualNodeMap getSpotAnimRasterizerMap();

    int getMenuHeight();

    int getClientPlane();

    int getCameraPitch();

    int getWorldCount();

    int getLoginState();

    String getUsername();

    TaskHandler getGlobalTaskHandler();

    int getCameraY();

    ReferenceTable getConfigReferenceTable();

    int getCameraZ();

    Map getMessageContainers();

    ImmutableNodeDeque getMessageDeque();

    ClanMate[] getClanMates();

    int getCameraX();

    Landscape getLandscape();

    int[][][] getTileHeights();

    byte[][][] getTileSettings();

    int getMenuWidth();

    int getLandscapeOffsetX();

    int getLandscapeOffsetY();

    int getLandscapeOffsetZ();

    NodeTable getItemContainers();

    int getAppletHeight();

    int getAppletWidth();

    int getCameraYaw();

    Canvas getCanvas();

    Player getLocalPlayer();

    Applet getGameEngineAsApplet();

    int getRevision();

    int getMouseX();

    int getMouseY();

    int getFps();

    GameEngine getGameEngine();

    int getLoadingState();

    Widget[][] getWidgets();

    int[] getGameSettings();

    int[] getSettings();

    String getClanChatName();

    String getClanChatOwner();

    int[] getCurrentLevels();

    int[] getExpLevels();

    int[] getExperiences();

    Friend[] getFriends();

    int getFriendsSize();

    int getGameDrawingMode();

    int getGameState();

    NodeDeque[][][] getGroundItems();

    int getMapAngle();

    int getMapOffset();

    int getMapScale();

    String[] getMenuActions();

    String[] getMenuOptions();

    int getMenuSize();

    int getMenuY();

    short getMouseZoom();

    Npc[] getNpcs();

    LinkedList getOnlineFriends();

    int getPlayerIndex();

    Player[] getPlayers();

    int getRepaintChatBox();

    int getRepaintFlag();

    boolean getResizableMode();

    int getRunEnergy();

    int getViewPortHeight();

    int getViewPortScale();

    int getViewPortWidth();

    World[] getWorlds();

    @SuppressWarnings("all")
    interface Unsafe {
        void setSpotAnimCache(DualNodeMap value);

        void setSpotAnimRasterizerMap(DualNodeMap value);

        void setMenuHeight(int value);

        void setClientPlane(int value);

        void setCameraPitch(int value);

        void setWorldCount(int value);

        void setLoginState(int value);

        void setUsername(String value);

        void setGlobalTaskHandler(TaskHandler value);

        void setCameraY(int value);

        void setConfigReferenceTable(ReferenceTable value);

        void setCameraZ(int value);

        void setMessageContainers(Map value);

        void setMessageDeque(ImmutableNodeDeque value);

        void setClanMates(ClanMate[] value);

        void setCameraX(int value);

        void setLandscape(Landscape value);

        void setTileHeights(int[][][] value);

        void setTileSettings(byte[][][] value);

        void setMenuWidth(int value);

        void setLandscapeOffsetX(int value);

        void setLandscapeOffsetY(int value);

        void setLandscapeOffsetZ(int value);

        void setItemContainers(NodeTable value);

        void setAppletHeight(int value);

        void setAppletWidth(int value);

        void setCameraYaw(int value);

        void setCanvas(Canvas value);

        void setLocalPlayer(Player value);

        void setGameEngineAsApplet(Applet value);

        void setRevision(int value);

        void setMouseX(int value);

        void setMouseY(int value);

        void setFps(int value);

        void setGameEngine(GameEngine value);

        void setLoadingState(int value);

        void setWidgets(Widget[][] value);

        void setGameSettings(int[] value);

        void setSettings(int[] value);

        void setClanChatName(String value);

        void setClanChatOwner(String value);

        void setCurrentLevels(int[] value);

        void setExpLevels(int[] value);

        void setExperiences(int[] value);

        void setFriends(Friend[] value);

        void setFriendsSize(int value);

        void setGameDrawingMode(int value);

        void setGameState(int value);

        void setGroundItems(NodeDeque[][][] value);

        void setMapAngle(int value);

        void setMapOffset(int value);

        void setMapScale(int value);

        void setMenuActions(String[] value);

        void setMenuOptions(String[] value);

        void setMenuSize(int value);

        void setMenuY(int value);

        void setMouseZoom(short value);

        void setNpcs(Npc[] value);

        void setOnlineFriends(LinkedList value);

        void setPlayerIndex(int value);

        void setPlayers(Player[] value);

        void setRepaintChatBox(int value);

        void setRepaintFlag(int value);

        void setResizableMode(boolean value);

        void setRunEnergy(int value);

        void setViewPortHeight(int value);

        void setViewPortScale(int value);

        void setViewPortWidth(int value);

        void setWorlds(World[] value);
    }
}
