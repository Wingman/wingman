package com.wingman.client.api.generated;

import java.util.Map;

@SuppressWarnings("all")
public interface Static {
    boolean decodeExternalPlayerMovement(BitBuffer bitBuffer, int playerId);

    void decodeNpcUpdate(boolean isLargeScene);

    ItemDefinition getItemDefinition(int itemId);

    NPCDefinition getNpcDefinition(int itemId);

    ObjectDefinition getObjectDefinition(int objectId);

    void pushMessage(int type, String prefix, String message, String suffix);

    void updateCharacterMovement(Character character, int arg1);

    void updateItemLayer(int x, int y);

    int getAppletHeight();

    int getAppletWidth();

    int getCameraPitch();

    int getCameraX();

    int getCameraY();

    int getCameraYaw();

    int getCameraZ();

    Client getClientInstance();

    int getClientPlane();

    int[] getCurrentLevels();

    int[] getExpLevels();

    int[] getExternalPlayerLocations();

    int getFps();

    int getGameDrawingMode();

    int[] getGameSettings();

    int getGameState();

    GrandExchangeOffer[] getGrandExchangeOffers();

    AbstractGraphicsBuffer getGraphicsBuffer();

    LinkedNodeList[][][] getGroundItems();

    NodeTable getItemContainers();

    Landscape getLandscape();

    Player getLocalPlayer();

    int getLoopCycle();

    int getMapAngle();

    int getMapOffset();

    int getMapScale();

    Map getMessageContainers();

    NPC[] getNpcs();

    int[] getPlayerSettings();

    Player[] getPlayers();

    int getRepaintChatBox();

    int getRepaintFlag();

    boolean getResizableMode();

    int[] getSkillExperiencePoints();

    int[][][] getTileHeights();

    byte[][][] getTileSettings();

    int getViewPortHeight();

    int getViewPortScale();

    int getViewPortWidth();

    int[] getWidgetSettings();

    Widget[][] getWidgets();

    @SuppressWarnings("all")
    interface Unsafe {
        void setAppletHeight(int value);

        void setAppletWidth(int value);

        void setCameraPitch(int value);

        void setCameraX(int value);

        void setCameraY(int value);

        void setCameraYaw(int value);

        void setCameraZ(int value);

        void setClientInstance(Client value);

        void setClientPlane(int value);

        void setCurrentLevels(int[] value);

        void setExpLevels(int[] value);

        void setExternalPlayerLocations(int[] value);

        void setFps(int value);

        void setGameDrawingMode(int value);

        void setGameSettings(int[] value);

        void setGameState(int value);

        void setGrandExchangeOffers(GrandExchangeOffer[] value);

        void setGraphicsBuffer(AbstractGraphicsBuffer value);

        void setGroundItems(LinkedNodeList[][][] value);

        void setItemContainers(NodeTable value);

        void setLandscape(Landscape value);

        void setLocalPlayer(Player value);

        void setLoopCycle(int value);

        void setMapAngle(int value);

        void setMapOffset(int value);

        void setMapScale(int value);

        void setMessageContainers(Map value);

        void setNpcs(NPC[] value);

        void setPlayerSettings(int[] value);

        void setPlayers(Player[] value);

        void setRepaintChatBox(int value);

        void setRepaintFlag(int value);

        void setResizableMode(boolean value);

        void setSkillExperiencePoints(int[] value);

        void setTileHeights(int[][][] value);

        void setTileSettings(byte[][][] value);

        void setViewPortHeight(int value);

        void setViewPortScale(int value);

        void setViewPortWidth(int value);

        void setWidgetSettings(int[] value);

        void setWidgets(Widget[][] value);
    }
}
