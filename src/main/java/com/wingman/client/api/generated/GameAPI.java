package com.wingman.client.api.generated;

import java.applet.Applet;
import java.awt.Canvas;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Map;

@SuppressWarnings("all")
public abstract class GameAPI {
    public static Static getterInstance;

    public static void drawRightClickMenu() {
        getterInstance.drawRightClickMenu();
    }

    public static void executeRuneScript(RuneScript arg0, int arg1) {
        getterInstance.executeRuneScript(arg0, arg1);
    }

    public static ItemDefinition getItemDefinition(int arg0) {
        return getterInstance.getItemDefinition(arg0);
    }

    public static int getVarbitValue(int arg0) {
        return getterInstance.getVarbitValue(arg0);
    }

    public static Varp getVarpForId(int arg0) {
        return getterInstance.getVarpForId(arg0);
    }

    public static boolean loadWidget(int arg0) {
        return getterInstance.loadWidget(arg0);
    }

    public static void openWidget(int arg0) {
        getterInstance.openWidget(arg0);
    }

    public static void processLoginStages() {
        getterInstance.processLoginStages();
    }

    public static void pushMessage(int arg0, String arg1, String arg2, String arg3) {
        getterInstance.pushMessage(arg0, arg1, arg2, arg3);
    }

    public static int getAppletHeight() {
        return getterInstance.getAppletHeight();
    }

    public static int getAppletWidth() {
        return getterInstance.getAppletWidth();
    }

    public static int getCameraPitch() {
        return getterInstance.getCameraPitch();
    }

    public static int getCameraX() {
        return getterInstance.getCameraX();
    }

    public static int getCameraY() {
        return getterInstance.getCameraY();
    }

    public static int getCameraYaw() {
        return getterInstance.getCameraYaw();
    }

    public static int getCameraZ() {
        return getterInstance.getCameraZ();
    }

    public static Canvas getCanvas() {
        return getterInstance.getCanvas();
    }

    public static String getClanChatName() {
        return getterInstance.getClanChatName();
    }

    public static String getClanChatOwner() {
        return getterInstance.getClanChatOwner();
    }

    public static ClanMate[] getClanMates() {
        return getterInstance.getClanMates();
    }

    public static int getClientPlane() {
        return getterInstance.getClientPlane();
    }

    public static ReferenceTable getConfigReferenceTable() {
        return getterInstance.getConfigReferenceTable();
    }

    public static int[] getCurrentLevels() {
        return getterInstance.getCurrentLevels();
    }

    public static int[] getExpLevels() {
        return getterInstance.getExpLevels();
    }

    public static int[] getExperiences() {
        return getterInstance.getExperiences();
    }

    public static int getFps() {
        return getterInstance.getFps();
    }

    public static Friend[] getFriends() {
        return getterInstance.getFriends();
    }

    public static int getFriendsSize() {
        return getterInstance.getFriendsSize();
    }

    public static int getGameDrawingMode() {
        return getterInstance.getGameDrawingMode();
    }

    public static GameEngine getGameEngine() {
        return getterInstance.getGameEngine();
    }

    public static Applet getGameEngineAsApplet() {
        return getterInstance.getGameEngineAsApplet();
    }

    public static int[] getGameSettings() {
        return getterInstance.getGameSettings();
    }

    public static int getGameState() {
        return getterInstance.getGameState();
    }

    public static TaskHandler getGlobalTaskHandler() {
        return getterInstance.getGlobalTaskHandler();
    }

    public static NodeDeque[][][] getGroundItems() {
        return getterInstance.getGroundItems();
    }

    public static NodeTable getItemContainers() {
        return getterInstance.getItemContainers();
    }

    public static Landscape getLandscape() {
        return getterInstance.getLandscape();
    }

    public static int getLandscapeOffsetX() {
        return getterInstance.getLandscapeOffsetX();
    }

    public static int getLandscapeOffsetY() {
        return getterInstance.getLandscapeOffsetY();
    }

    public static int getLandscapeOffsetZ() {
        return getterInstance.getLandscapeOffsetZ();
    }

    public static int getLoadingState() {
        return getterInstance.getLoadingState();
    }

    public static Player getLocalPlayer() {
        return getterInstance.getLocalPlayer();
    }

    public static int getLoginState() {
        return getterInstance.getLoginState();
    }

    public static int getMapAngle() {
        return getterInstance.getMapAngle();
    }

    public static int getMapOffset() {
        return getterInstance.getMapOffset();
    }

    public static int getMapScale() {
        return getterInstance.getMapScale();
    }

    public static String[] getMenuActions() {
        return getterInstance.getMenuActions();
    }

    public static int getMenuHeight() {
        return getterInstance.getMenuHeight();
    }

    public static String[] getMenuOptions() {
        return getterInstance.getMenuOptions();
    }

    public static int getMenuSize() {
        return getterInstance.getMenuSize();
    }

    public static int getMenuWidth() {
        return getterInstance.getMenuWidth();
    }

    public static int getMenuY() {
        return getterInstance.getMenuY();
    }

    public static Map getMessageContainers() {
        return getterInstance.getMessageContainers();
    }

    public static ImmutableNodeDeque getMessageDeque() {
        return getterInstance.getMessageDeque();
    }

    public static int getMouseX() {
        return getterInstance.getMouseX();
    }

    public static int getMouseY() {
        return getterInstance.getMouseY();
    }

    public static short getMouseZoom() {
        return getterInstance.getMouseZoom();
    }

    public static Npc[] getNpcs() {
        return getterInstance.getNpcs();
    }

    public static LinkedList getOnlineFriends() {
        return getterInstance.getOnlineFriends();
    }

    public static int getPlayerIndex() {
        return getterInstance.getPlayerIndex();
    }

    public static Player[] getPlayers() {
        return getterInstance.getPlayers();
    }

    public static int getRepaintChatBox() {
        return getterInstance.getRepaintChatBox();
    }

    public static int getRepaintFlag() {
        return getterInstance.getRepaintFlag();
    }

    public static boolean getResizableMode() {
        return getterInstance.getResizableMode();
    }

    public static int getRevision() {
        return getterInstance.getRevision();
    }

    public static int getRunEnergy() {
        return getterInstance.getRunEnergy();
    }

    public static int[] getSettings() {
        return getterInstance.getSettings();
    }

    public static DualNodeMap getSpotAnimCache() {
        return getterInstance.getSpotAnimCache();
    }

    public static DualNodeMap getSpotAnimRasterizerMap() {
        return getterInstance.getSpotAnimRasterizerMap();
    }

    public static int[][][] getTileHeights() {
        return getterInstance.getTileHeights();
    }

    public static byte[][][] getTileSettings() {
        return getterInstance.getTileSettings();
    }

    public static String getUsername() {
        return getterInstance.getUsername();
    }

    public static int getViewPortHeight() {
        return getterInstance.getViewPortHeight();
    }

    public static int getViewPortScale() {
        return getterInstance.getViewPortScale();
    }

    public static int getViewPortWidth() {
        return getterInstance.getViewPortWidth();
    }

    public static Widget[][] getWidgets() {
        return getterInstance.getWidgets();
    }

    public static int getWorldCount() {
        return getterInstance.getWorldCount();
    }

    public static World[] getWorlds() {
        return getterInstance.getWorlds();
    }

    @SuppressWarnings("all")
    public abstract static class Unsafe {
        public static Static.Unsafe setterInstance;

        public static void setAppletHeight(int value) {
            setterInstance.setAppletHeight(value);
        }

        public static void setAppletWidth(int value) {
            setterInstance.setAppletWidth(value);
        }

        public static void setCameraPitch(int value) {
            setterInstance.setCameraPitch(value);
        }

        public static void setCameraX(int value) {
            setterInstance.setCameraX(value);
        }

        public static void setCameraY(int value) {
            setterInstance.setCameraY(value);
        }

        public static void setCameraYaw(int value) {
            setterInstance.setCameraYaw(value);
        }

        public static void setCameraZ(int value) {
            setterInstance.setCameraZ(value);
        }

        public static void setCanvas(Canvas value) {
            setterInstance.setCanvas(value);
        }

        public static void setClanChatName(String value) {
            setterInstance.setClanChatName(value);
        }

        public static void setClanChatOwner(String value) {
            setterInstance.setClanChatOwner(value);
        }

        public static void setClanMates(ClanMate[] value) {
            setterInstance.setClanMates(value);
        }

        public static void setClientPlane(int value) {
            setterInstance.setClientPlane(value);
        }

        public static void setConfigReferenceTable(ReferenceTable value) {
            setterInstance.setConfigReferenceTable(value);
        }

        public static void setCurrentLevels(int[] value) {
            setterInstance.setCurrentLevels(value);
        }

        public static void setExpLevels(int[] value) {
            setterInstance.setExpLevels(value);
        }

        public static void setExperiences(int[] value) {
            setterInstance.setExperiences(value);
        }

        public static void setFps(int value) {
            setterInstance.setFps(value);
        }

        public static void setFriends(Friend[] value) {
            setterInstance.setFriends(value);
        }

        public static void setFriendsSize(int value) {
            setterInstance.setFriendsSize(value);
        }

        public static void setGameDrawingMode(int value) {
            setterInstance.setGameDrawingMode(value);
        }

        public static void setGameEngine(GameEngine value) {
            setterInstance.setGameEngine(value);
        }

        public static void setGameEngineAsApplet(Applet value) {
            setterInstance.setGameEngineAsApplet(value);
        }

        public static void setGameSettings(int[] value) {
            setterInstance.setGameSettings(value);
        }

        public static void setGameState(int value) {
            setterInstance.setGameState(value);
        }

        public static void setGlobalTaskHandler(TaskHandler value) {
            setterInstance.setGlobalTaskHandler(value);
        }

        public static void setGroundItems(NodeDeque[][][] value) {
            setterInstance.setGroundItems(value);
        }

        public static void setItemContainers(NodeTable value) {
            setterInstance.setItemContainers(value);
        }

        public static void setLandscape(Landscape value) {
            setterInstance.setLandscape(value);
        }

        public static void setLandscapeOffsetX(int value) {
            setterInstance.setLandscapeOffsetX(value);
        }

        public static void setLandscapeOffsetY(int value) {
            setterInstance.setLandscapeOffsetY(value);
        }

        public static void setLandscapeOffsetZ(int value) {
            setterInstance.setLandscapeOffsetZ(value);
        }

        public static void setLoadingState(int value) {
            setterInstance.setLoadingState(value);
        }

        public static void setLocalPlayer(Player value) {
            setterInstance.setLocalPlayer(value);
        }

        public static void setLoginState(int value) {
            setterInstance.setLoginState(value);
        }

        public static void setMapAngle(int value) {
            setterInstance.setMapAngle(value);
        }

        public static void setMapOffset(int value) {
            setterInstance.setMapOffset(value);
        }

        public static void setMapScale(int value) {
            setterInstance.setMapScale(value);
        }

        public static void setMenuActions(String[] value) {
            setterInstance.setMenuActions(value);
        }

        public static void setMenuHeight(int value) {
            setterInstance.setMenuHeight(value);
        }

        public static void setMenuOptions(String[] value) {
            setterInstance.setMenuOptions(value);
        }

        public static void setMenuSize(int value) {
            setterInstance.setMenuSize(value);
        }

        public static void setMenuWidth(int value) {
            setterInstance.setMenuWidth(value);
        }

        public static void setMenuY(int value) {
            setterInstance.setMenuY(value);
        }

        public static void setMessageContainers(Map value) {
            setterInstance.setMessageContainers(value);
        }

        public static void setMessageDeque(ImmutableNodeDeque value) {
            setterInstance.setMessageDeque(value);
        }

        public static void setMouseX(int value) {
            setterInstance.setMouseX(value);
        }

        public static void setMouseY(int value) {
            setterInstance.setMouseY(value);
        }

        public static void setMouseZoom(short value) {
            setterInstance.setMouseZoom(value);
        }

        public static void setNpcs(Npc[] value) {
            setterInstance.setNpcs(value);
        }

        public static void setOnlineFriends(LinkedList value) {
            setterInstance.setOnlineFriends(value);
        }

        public static void setPlayerIndex(int value) {
            setterInstance.setPlayerIndex(value);
        }

        public static void setPlayers(Player[] value) {
            setterInstance.setPlayers(value);
        }

        public static void setRepaintChatBox(int value) {
            setterInstance.setRepaintChatBox(value);
        }

        public static void setRepaintFlag(int value) {
            setterInstance.setRepaintFlag(value);
        }

        public static void setResizableMode(boolean value) {
            setterInstance.setResizableMode(value);
        }

        public static void setRevision(int value) {
            setterInstance.setRevision(value);
        }

        public static void setRunEnergy(int value) {
            setterInstance.setRunEnergy(value);
        }

        public static void setSettings(int[] value) {
            setterInstance.setSettings(value);
        }

        public static void setSpotAnimCache(DualNodeMap value) {
            setterInstance.setSpotAnimCache(value);
        }

        public static void setSpotAnimRasterizerMap(DualNodeMap value) {
            setterInstance.setSpotAnimRasterizerMap(value);
        }

        public static void setTileHeights(int[][][] value) {
            setterInstance.setTileHeights(value);
        }

        public static void setTileSettings(byte[][][] value) {
            setterInstance.setTileSettings(value);
        }

        public static void setUsername(String value) {
            setterInstance.setUsername(value);
        }

        public static void setViewPortHeight(int value) {
            setterInstance.setViewPortHeight(value);
        }

        public static void setViewPortScale(int value) {
            setterInstance.setViewPortScale(value);
        }

        public static void setViewPortWidth(int value) {
            setterInstance.setViewPortWidth(value);
        }

        public static void setWidgets(Widget[][] value) {
            setterInstance.setWidgets(value);
        }

        public static void setWorldCount(int value) {
            setterInstance.setWorldCount(value);
        }

        public static void setWorlds(World[] value) {
            setterInstance.setWorlds(value);
        }
    }
}
