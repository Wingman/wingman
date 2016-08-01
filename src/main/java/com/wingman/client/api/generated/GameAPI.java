package com.wingman.client.api.generated;

import java.awt.Canvas;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Map;

@SuppressWarnings("all")
public abstract class GameAPI {
    public static Static getterInstance;

    public static boolean decodeExternalPlayerMovement(BitBuffer arg0, int arg1) {
        return getterInstance.decodeExternalPlayerMovement(arg0, arg1);
    }

    public static ItemDefinition getItemDefinition(int arg0) {
        return getterInstance.getItemDefinition(arg0);
    }

    public static NPCDefinition getNPCDefinition(int arg0) {
        return getterInstance.getNPCDefinition(arg0);
    }

    public static void pushMessage(int arg0, String arg1, String arg2, String arg3) {
        getterInstance.pushMessage(arg0, arg1, arg2, arg3);
    }

    public static void updateCharacterMovement(Character arg0, int arg1) {
        getterInstance.updateCharacterMovement(arg0, arg1);
    }

    public static Canvas getCanvas() {
        return getterInstance.getCanvas();
    }

    public static int getGameDrawingMode() {
        return getterInstance.getGameDrawingMode();
    }

    public static int getGameState() {
        return getterInstance.getGameState();
    }

    public static int getLoopCycle() {
        return getterInstance.getLoopCycle();
    }

    public static Map getMessageContainers() {
        return getterInstance.getMessageContainers();
    }

    public static NPC[] getNpcs() {
        return getterInstance.getNpcs();
    }

    public static Player[] getPlayers() {
        return getterInstance.getPlayers();
    }

    @SuppressWarnings("all")
    public abstract static class Unsafe {
        public static Static.Unsafe setterInstance;

        public static void setCanvas(Canvas value) {
            setterInstance.setCanvas(value);
        }

        public static void setGameDrawingMode(int value) {
            setterInstance.setGameDrawingMode(value);
        }

        public static void setGameState(int value) {
            setterInstance.setGameState(value);
        }

        public static void setLoopCycle(int value) {
            setterInstance.setLoopCycle(value);
        }

        public static void setMessageContainers(Map value) {
            setterInstance.setMessageContainers(value);
        }

        public static void setNpcs(NPC[] value) {
            setterInstance.setNpcs(value);
        }

        public static void setPlayers(Player[] value) {
            setterInstance.setPlayers(value);
        }
    }
}
