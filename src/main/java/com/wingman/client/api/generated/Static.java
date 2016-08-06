package com.wingman.client.api.generated;

import java.awt.Canvas;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Map;

@SuppressWarnings("all")
public interface Static {
    boolean decodeExternalPlayerMovement(BitBuffer arg0, int arg1);

    ItemDefinition getItemDefinition(int arg0);

    NPCDefinition getNpcDefinition(int arg0);

    void pushMessage(int arg0, String arg1, String arg2, String arg3);

    void updateCharacterMovement(Character arg0, int arg1);

    int getCameraPitch();

    int getCameraX();

    int getCameraY();

    int getCameraYaw();

    int getCameraZ();

    Canvas getCanvas();

    int getClientPlane();

    int[] getExternalPlayerLocations();

    int getGameDrawingMode();

    int[] getGameSettings();

    int getGameState();

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

    boolean getResizableMode();

    int[][][] getTileHeights();

    byte[][][] getTileSettings();

    int getViewPortHeight();

    int getViewPortScale();

    int getViewPortWidth();

    int[] getWidgetSettings();

    @SuppressWarnings("all")
    interface Unsafe {
        void setCameraPitch(int value);

        void setCameraX(int value);

        void setCameraY(int value);

        void setCameraYaw(int value);

        void setCameraZ(int value);

        void setCanvas(Canvas value);

        void setClientPlane(int value);

        void setExternalPlayerLocations(int[] value);

        void setGameDrawingMode(int value);

        void setGameSettings(int[] value);

        void setGameState(int value);

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

        void setResizableMode(boolean value);

        void setTileHeights(int[][][] value);

        void setTileSettings(byte[][][] value);

        void setViewPortHeight(int value);

        void setViewPortScale(int value);

        void setViewPortWidth(int value);

        void setWidgetSettings(int[] value);
    }
}
