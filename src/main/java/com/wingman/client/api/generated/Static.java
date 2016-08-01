package com.wingman.client.api.generated;

import java.awt.Canvas;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Map;

@SuppressWarnings("all")
public interface Static {
    boolean decodeExternalPlayerMovement(BitBuffer arg0, int arg1);

    ItemDefinition getItemDefinition(int arg0);

    NPCDefinition getNPCDefinition(int arg0);

    void pushMessage(int arg0, String arg1, String arg2, String arg3);

    void updateCharacterMovement(Character arg0, int arg1);

    Canvas getCanvas();

    int getGameDrawingMode();

    int getGameState();

    int getLoopCycle();

    Map getMessageContainers();

    NPC[] getNpcs();

    Player[] getPlayers();

    @SuppressWarnings("all")
    interface Unsafe {
        void setCanvas(Canvas value);

        void setGameDrawingMode(int value);

        void setGameState(int value);

        void setLoopCycle(int value);

        void setMessageContainers(Map value);

        void setNpcs(NPC[] value);

        void setPlayers(Player[] value);
    }
}
